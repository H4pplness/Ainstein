package dongpb.agenticai.orchestratorservice.database.entities.agent;

import dongpb.agenticai.orchestratorservice.database.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "agents")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer agentId;

    private Integer modelId;
    private String name;
    private String description;
    private Integer parentAgentId;
}
