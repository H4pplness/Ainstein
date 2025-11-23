package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method;


import dongpb.agenticai.orchestratorservice.domain.model.AIRequest;
import dongpb.agenticai.orchestratorservice.domain.model.AIService;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.Agent;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.AgentAdministrationService;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.helper.AgentHelper;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.helper.PromptHelper;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.function_request.UseAgentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UseAgentFunction implements Function<UseAgentRequest> {
    @Override
    public FunctionType getType() {
        return FunctionType.USE_AGENT;
    }

    @Override
    public Object execute(UseAgentRequest request) {


        return null;
    }

    @Override
    public String describe() {
        return "";
    }
}
