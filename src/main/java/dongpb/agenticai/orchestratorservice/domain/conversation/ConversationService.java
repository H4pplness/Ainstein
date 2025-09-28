package dongpb.agenticai.orchestratorservice.domain.conversation;

import dongpb.agenticai.orchestratorservice.application.exception.BaseException;
import dongpb.agenticai.orchestratorservice.application.exception.Errors;
import dongpb.agenticai.orchestratorservice.database.entities.ConversationEntity;
import dongpb.agenticai.orchestratorservice.database.entities.MessageEntity;
import dongpb.agenticai.orchestratorservice.database.repositories.ConversationRepository;
import dongpb.agenticai.orchestratorservice.database.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public Conversation getById(String conversationId){
        ConversationEntity conversationEntity = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new BaseException(Errors.NOT_FOUND));
        List<MessageEntity> messageEntities = messageRepository.findByConversationId(conversationId);
        return Conversation.of(conversationEntity,messageEntities);
    }

    public void save(Conversation conversation){
        ConversationEntity conversationEntity = conversation.toEntity();
        if (conversationEntity.getConversationId() == null) {
            conversationEntity.setConversationId(UUID.randomUUID().toString());
        }
        List<MessageEntity> messageEntities = conversation.toMessageEntities();

        conversationRepository.save(conversationEntity);
        messageRepository.saveAll(messageEntities);
    }

    public void delete(Conversation conversation){
        ConversationEntity conversationEntity = conversation.toEntity();
        List<MessageEntity> messageEntities = conversation.toMessageEntities();

        conversationRepository.delete(conversationEntity);
        messageRepository.deleteAll(messageEntities);
    }

    public void delete(String conversationId){
        ConversationEntity conversationEntity = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new BaseException(Errors.NOT_FOUND));
        List<MessageEntity> messageEntities = messageRepository.findByConversationId(conversationId);

        conversationRepository.delete(conversationEntity);
        messageRepository.deleteAll(messageEntities);
    }
}
