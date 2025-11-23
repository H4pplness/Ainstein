package dongpb.agenticai.orchestratorservice.database.repositories;

import dongpb.agenticai.orchestratorservice.database.entities.ParticipantCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantCodeRepository extends JpaRepository<ParticipantCodeEntity, String> {
}
