package dongpb.agenticai.orchestratorservice.database.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "participant_codes")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantCodeEntity {
    @Id
    private String participantCode; //
    private String type; // person / agent
    private String referenceId; // id reference
}
