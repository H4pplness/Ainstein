package dongpb.agenticai.orchestratorservice.database.repositories;

import dongpb.agenticai.orchestratorservice.database.entities.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity, String> {
    List<ConversationEntity> findByUserId(String userId);
}
