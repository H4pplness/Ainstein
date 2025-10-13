package dongpb.agenticai.orchestratorservice.database.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "conversations")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationEntity extends BaseEntity{
    @Id
    String conversationId;

    String title;

//    @Column(nullable = false)
    String userId;

    @JdbcTypeCode(SqlTypes.JSON)
    Map<String,Object> metadata;
}
