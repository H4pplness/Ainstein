package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.function_request;

import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.FunctionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReturnResponseRequest extends FunctionRequest {
    String message;

    @Override
    public FunctionType getType() {
        return FunctionType.RETURN;
    }
}
