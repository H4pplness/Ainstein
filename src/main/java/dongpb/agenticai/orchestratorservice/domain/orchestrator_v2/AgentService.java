package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public interface AgentService {
    Object execute(String request);
}
