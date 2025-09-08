package dongpb.agenticai.orchestratorservice.domain.orchestrator;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrchestratorResponse<T> {
    Role role;
    T content;

    public enum Role {
        USER, ASSISTANT, SYSTEM
    }
}
