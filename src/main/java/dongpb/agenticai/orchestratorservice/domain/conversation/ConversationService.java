package dongpb.agenticai.orchestratorservice.domain.conversation;

import dongpb.agenticai.orchestratorservice.application.exception.BaseException;
import dongpb.agenticai.orchestratorservice.application.exception.Errors;
import dongpb.agenticai.orchestratorservice.database.entities.ConversationEntity;
import dongpb.agenticai.orchestratorservice.database.entities.ConversationMessageEntity;
import dongpb.agenticai.orchestratorservice.database.repositories.ConversationMessageRepository;
import dongpb.agenticai.orchestratorservice.database.repositories.ConversationRepository;
import jdk.dynalink.linker.LinkerServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final ConversationMessageRepository conversationMessageEntity;
    private final ConversationMessageRepository conversationMessageRepository;
    private final KafkaParticipantManager kafkaParticipantManager;

    public Conversation getConversation(String conversationId){
        Conversation conversation = new Conversation();
        conversation.setConversationId(conversationId);
        ConversationEntity conversationEntity = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new BaseException(Errors.BAD_REQUEST));

        List<ConversationMessageEntity> messages = conversationMessageRepository.findAllByConversationId(conversationId);
        conversation.setMessages(messages);

        return conversation;
    }

    public Object sendMessage(String conversationId, Conversation.Message message) {
        if (conversationId == null) {
            ConversationEntity conversationEntity = new ConversationEntity();
            conversationRepository.save(conversationEntity);
            conversationId = conversationEntity.getConversationId();
        }

        ConversationMessageEntity conversationMessageEntity = createConversationMessage(conversationId,message);

        message.setConversationId(conversationId);
        // send a message to receiver queue
        kafkaParticipantManager.sendMessage(message.getReceiverCode(),message);
        /**
         * Send a message to the agent
         * Push messages to queue -> Agent get from queue and handle it -> Return response to
         */

        conversationMessageRepository.save(conversationMessageEntity);
        return conversationMessageEntity;
    }

    private ConversationMessageEntity createConversationMessage(String conversationId, Conversation.Message message) {
        ConversationMessageEntity conversationMessageEntity = new ConversationMessageEntity();
        conversationMessageEntity.setConversationId(conversationId);
        conversationMessageEntity.setContent(message.getContent());
        conversationMessageEntity.setSenderCode(message.getSenderCode());
        conversationMessageEntity.setReceiverCode(message.getReceiverCode());
        return conversationMessageEntity;
    }
}
