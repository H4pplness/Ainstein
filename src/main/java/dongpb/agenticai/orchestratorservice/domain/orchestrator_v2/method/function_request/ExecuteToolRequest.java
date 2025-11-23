package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.function_request;

import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.FunctionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExecuteToolRequest extends FunctionRequest {
    String tool;
    Map<String,Object> input;

    @Override
    public FunctionType getType() {
        return FunctionType.EXECUTE_TOOL;
    }
}
