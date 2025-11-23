package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2;

import org.springframework.stereotype.Service;

@Service
public interface AgentService {
    Object chat(AgentRequest request);
    Object execute(Object input);
}
