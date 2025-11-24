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
}
