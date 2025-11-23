package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.impl;

import dongpb.agenticai.orchestratorservice.database.repositories.AgentRepository;
import dongpb.agenticai.orchestratorservice.database.repositories.ConversationRepository;
import dongpb.agenticai.orchestratorservice.domain.model.AIRequest;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.Agent;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.AgentAdministrationService;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.AgentRequest;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.AgentService;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.helper.AgentHelper;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.helper.PromptHelper;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.FunctionRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {
    private final FunctionRegistry functionRegistry;
    private final PromptHelper promptHelper;
    private final AgentRepository agentRepository;
    private final ConversationRepository conversationRepository;
    private final AgentHelper agentHelper;
    private final AgentAdministrationService agentAdministrationService;

    @Override
    public Object chat(AgentRequest request) {
        Agent agent = agentAdministrationService.getAgentAndNearestChildren(request.getAgentId());
//        AIRequest aiRequest =



        return null;
    }

    @Override
    public Object execute(Object input) {
        return null;
    }
}
