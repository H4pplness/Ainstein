package dongpb.agenticai.orchestratorservice.application.request;

import lombok.Data;

@Data
public class CreateParticipantRequest {
    private String referenceId;
    private String type; // human, agent
}
