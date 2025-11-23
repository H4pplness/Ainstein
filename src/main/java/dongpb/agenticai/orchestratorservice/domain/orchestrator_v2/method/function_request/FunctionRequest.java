package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.function_request;

import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.FunctionType;
import lombok.Data;

@Data
public abstract class FunctionRequest {
    public abstract FunctionType getType();
}
