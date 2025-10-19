package dongpb.agenticai.orchestratorservice.database.repositories;

import dongpb.agenticai.orchestratorservice.database.entities.agent.AgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<AgentEntity, Integer> {
    @Query(value = """
            WITH RECURSIVE descendants AS (
                SELECT *\s
                FROM agents\s
                WHERE agent_id = :rootId
                UNION ALL
                SELECT n.*
                FROM agents n
                INNER JOIN descendants d\s
                    ON n.parent_agent_id = d.agent_id
            )
            SELECT *\s
            FROM descendants;
    """,nativeQuery = true)
    List<AgentEntity> findAllByBusinessId(@Param("rootId") Integer rootId);
}
