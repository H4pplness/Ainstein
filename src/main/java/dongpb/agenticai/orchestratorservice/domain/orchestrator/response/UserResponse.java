package dongpb.agenticai.orchestratorservice.domain.orchestrator.response;

import lombok.Data;

import java.util.Map;

@Data
public class UserResponse {
    String content;
    Map<String, Object> metadata;
}
