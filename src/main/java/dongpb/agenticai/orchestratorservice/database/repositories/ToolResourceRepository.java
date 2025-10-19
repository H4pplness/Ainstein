package dongpb.agenticai.orchestratorservice.database.repositories;

import dongpb.agenticai.orchestratorservice.database.entities.agent.ToolResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolResourceRepository extends JpaRepository<ToolResourceEntity, Integer> {
    List<ToolResourceEntity> findByAgentId(Integer agentId);
}
