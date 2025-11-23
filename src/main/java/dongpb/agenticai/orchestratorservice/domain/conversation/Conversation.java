package dongpb.agenticai.orchestratorservice.domain.conversation;

import dongpb.agenticai.orchestratorservice.database.entities.ConversationMessageEntity;
import dongpb.agenticai.orchestratorservice.database.entities.ParticipantCodeEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Conversation {
    private String conversationId;
    private List<Message> messages;

    @Data
    public static class Message {
        private String senderCode;
        private String receiverCode;
        private Map<String,Object> content;
        private LocalDateTime sendAt;
    }


    public void setMessages(List<ConversationMessageEntity> messageEntities) {
        messages = messageEntities.stream().map(e->{
            Message message = new Message();
            message.setSenderCode(e.getSenderCode());
            message.setReceiverCode(e.getReceiverCode());
            message.setContent(e.getContent());
            message.setSendAt(e.getCreatedDate());
            return message;
        }).toList();
    }
}
