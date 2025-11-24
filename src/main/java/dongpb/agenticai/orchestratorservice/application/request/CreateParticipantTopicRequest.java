package dongpb.agenticai.orchestratorservice.application.request;

import lombok.Data;

@Data
public class CreateParticipantTopicRequest {
    private String code;
    private String handlerType;
}
