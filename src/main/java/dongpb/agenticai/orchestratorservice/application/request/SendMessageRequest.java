package dongpb.agenticai.orchestratorservice.application.request;

import dongpb.agenticai.orchestratorservice.domain.conversation.Conversation;
import lombok.Data;

@Data
public class SendMessageRequest {
    private String conversationId;
    private Conversation.Message message;
}
