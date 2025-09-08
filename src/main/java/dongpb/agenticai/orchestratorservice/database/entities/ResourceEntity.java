package dongpb.agenticai.orchestratorservice.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "resources")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceEntity extends BaseEntity{
    @Id
    @Column(unique = true)
    String resourceCode;

    String resourceName;
    String description;
    String type; // resource
}
