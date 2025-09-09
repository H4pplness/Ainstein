package dongpb.agenticai.orchestratorservice.database.repositories;

import dongpb.agenticai.orchestratorservice.database.entities.FunctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionRepository extends JpaRepository<FunctionEntity, String> {
    List<FunctionEntity> findByResourceCode(String resourceCode);
}
