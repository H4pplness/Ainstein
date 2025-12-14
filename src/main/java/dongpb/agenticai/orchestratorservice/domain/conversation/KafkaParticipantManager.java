package dongpb.agenticai.orchestratorservice.domain.conversation;

import dongpb.agenticai.orchestratorservice.application.exception.BaseException;
import dongpb.agenticai.orchestratorservice.application.exception.Errors;
import dongpb.agenticai.orchestratorservice.common.JsonUtils;
import dongpb.agenticai.orchestratorservice.database.entities.ParticipantCodeEntity;
import dongpb.agenticai.orchestratorservice.database.repositories.ParticipantCodeRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
@Setter
public class KafkaParticipantManager {
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory;

    private final AdminClient adminClient;

    private final EventHandlerRegistry eventHandlerRegistry;

    private static final String TOPIC_PREFIX = "participant";
    private final Map<String, Participant> participants = new ConcurrentHashMap<>(); //

    private String getTopicName(String code) {
        return TOPIC_PREFIX + "-" + code;
    }

    /**
     * Tạo participant mới
     */
    public void createParticipant(String code, EventHandler handler) {
        createParticipant(code, null ,handler, 3, (short) 1);
    }

    protected void createParticipant(String code, Map<String,Object> metadata, EventHandler eventHandler) {
        createParticipant(code,metadata,eventHandler,3,(short) 1);
    }

    /**
     * Kiểm tra participant có tồn tại không
     */
    protected boolean participantExists(String participantCode) {
        return participants.containsKey(participantCode);
    }

    /**
     * Tạo participant với cấu hình tùy chỉnh
     */
    public void createParticipant(String code, Map<String, Object> metadata , EventHandler handler,
                                  int numPartitions, short replicationFactor) {
        if (handler == null) {
            handler = eventHandlerRegistry.getHandler(AgentHandler.class);
        }

        if (participants.containsKey(code)) {
            log.warn("participant {} đã tồn tại", code);
            return;
        }

        String topicName = getTopicName(code);

        // Tạo topic
        try {
            NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
            adminClient.createTopics(Collections.singleton(newTopic)).all().get();
            log.info("Đã tạo topic: {}", topicName);
        } catch (Exception e) {
            log.info("Topic {} có thể đã tồn tại: {}", topicName, e.getMessage());
        }

        // Tạo consumer container
        ConcurrentMessageListenerContainer<String, Object> container =
                containerFactory.createContainer(topicName);

        container.getContainerProperties().setGroupId(groupId + "-" + code);

        EventHandler finalHandler = handler;
        container.setupMessageListener((MessageListener<String, Object>) record -> {
            try {
                log.error(JsonUtils.toJson(record.value()));
                Object message = record.value();
                finalHandler.handle(message);
            } catch (Exception e) {
                log.error("Lỗi xử lý message từ participant {}: {}", code, e.getMessage(), e);
            }
        });

        container.start();

        participants.put(code, new Participant(code, metadata, container, handler));
        log.info("Đã tạo và khởi động consumer cho participant: {}", code);
    }

    /**
     * Xóa participant
     */
    public void removeParticipant(String participantCode) {
        Participant info = participants.remove(participantCode);
        if (info == null) {
            log.warn("Participant {} không tồn tại", participantCode);
            return;
        }

        // Dừng container
        if (info.container != null && info.container.isRunning()) {
            info.container.stop();
            log.info("Đã dừng consumer cho participant: {}", participantCode);
        }

        // Xóa topic (tùy chọn - có thể giữ lại topic để không mất dữ liệu)
        try {
            adminClient.deleteTopics(Collections.singleton(info.code)).all().get();
            log.info("Đã xóa topic: {}", info.code);
        } catch (Exception e) {
            log.error("Lỗi khi xóa topic {}: {}", info.code, e.getMessage());
        }
    }

    /**
     * Gửi message đến participant
     */
    public void sendMessage(String participantCode, Conversation.Message message) {
        if (!participants.containsKey(participantCode)) {
            throw new IllegalArgumentException("Participant không tồn tại: " + participantCode);
        }

        if (!message.getReceiverCode().equals(participantCode)) {
            log.error("Người nhận phải trùng với participantCode !");
            throw new BaseException(Errors.BAD_REQUEST);
        }

        String topicName = getTopicName(participantCode);
        kafkaTemplate.send(topicName, participantCode, JsonUtils.toJson(message));
    }

    /**
     * Lấy danh sách participants
     */
    public Map<String, Participant> getAllParticipants() {
        return Collections.unmodifiableMap(participants);
    }


    @Data
    @NoArgsConstructor
    public static class Participant {
        private String code;
        private ConcurrentMessageListenerContainer<String, Object> container;
        private Map<String,Object> metadata;
        private EventHandler eventHandler;

        public Participant(String code, Map<String,Object> metadata,ConcurrentMessageListenerContainer<String, Object> container, EventHandler eventHandler) {
            this.container = container;
            this.eventHandler = eventHandler;
        }
    }
}
