package dongpb.agenticai.orchestratorservice.domain.conversation;

import dongpb.agenticai.orchestratorservice.database.entities.ConversationEntity;
import dongpb.agenticai.orchestratorservice.database.entities.MessageEntity;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class Conversation {
    String conversationId;
    String title;
    Map<String, Object> metadata;
    List<Message> messages;

    protected static Conversation of(ConversationEntity conversationEntity, List<MessageEntity> messageEntities) {
        Conversation conversation = new Conversation();
        conversation.setConversationId(conversationEntity.getConversationId());
        conversation.setTitle(conversationEntity.getTitle());
        conversation.setMetadata(conversationEntity.getMetadata());

        conversation.setMessages(new ArrayList<>());

        for (MessageEntity messageEntity : messageEntities) {
            conversation.addMessage(Message.of(messageEntity));
        }

        return conversation;
    }

    protected ConversationEntity toEntity() {
        ConversationEntity conversationEntity = new ConversationEntity();
        conversationEntity.setConversationId(this.conversationId);
        conversationEntity.setTitle(this.title);
        conversationEntity.setMetadata(this.metadata);

        return conversationEntity;
    }

    protected List<MessageEntity> toMessageEntities() {
        List<MessageEntity> messageEntities = new ArrayList<>();
        for (Message message : this.messages) {
            MessageEntity messageEntity = message.toEntity();
            messageEntity.setConversationId(this.conversationId);

            messageEntities.add(messageEntity);
        }

        return messageEntities;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void addMessages(List<Message> messages) {
        this.messages.addAll(messages);
    }

    @Data
    public static class Message {
        String role;
        String content;

        protected static Message of(MessageEntity messageEntity) {
            Message message = new Message();
            message.setRole(messageEntity.getRole());
            message.setContent(messageEntity.getContent());

            return message;
        }

        private MessageEntity toEntity() {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setRole(this.role);
            messageEntity.setContent(this.content);

            return messageEntity;
        }
    }
}
