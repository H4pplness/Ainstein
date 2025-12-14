package dongpb.agenticai.orchestratorservice.domain.conversation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public enum ParticipantType {
    AGENT("agent"),PERSON("person");

    private final String type;

    public static ParticipantType fromType(String type) {
        for (ParticipantType participantType :  ParticipantType.values()) {
            if (participantType.getType().equals(type)) {
                return participantType;
            }
        }
        return null;
    }
}
