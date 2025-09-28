package dongpb.agenticai.orchestratorservice.database.repositories;

import dongpb.agenticai.orchestratorservice.database.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, String> {
    List<MessageEntity> findByConversationId(String conversationId);
}
