package dongpb.agenticai.orchestratorservice.database.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "functions")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long functionId;

    String description;

    @Column(nullable = false)
    String resourceCode;

    String type;

    @JdbcTypeCode(SqlTypes.JSON)
    Map<String,Object> metadata;
}
