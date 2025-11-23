package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method;

import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.function_request.ExecuteToolRequest;
import dongpb.agenticai.orchestratorservice.domain.tool.Tool;
import dongpb.agenticai.orchestratorservice.domain.tool.ToolRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExecuteToolFunction implements Function<ExecuteToolRequest> {
    private final ToolRegistry toolRegistry;

    @Override
    public FunctionType getType() {
        return FunctionType.EXECUTE_TOOL;
    }

    @Override
    public Object execute(ExecuteToolRequest request) {
        Tool tool = toolRegistry.get(request.getTool());
        return tool.execute(request.getInput());
    }

    @Override
    public String describe() {
        return "";
    }
}
