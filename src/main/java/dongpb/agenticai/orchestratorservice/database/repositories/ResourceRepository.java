package dongpb.agenticai.orchestratorservice.database.repositories;

import dongpb.agenticai.orchestratorservice.database.entities.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, String> {
    Optional<ResourceEntity> findByResourceCode(String code);
}
