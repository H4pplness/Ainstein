package dongpb.agenticai.orchestratorservice.domain.agents;

import dongpb.agenticai.orchestratorservice.domain.conversation.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentService {
    private final ConversationService conversationService;

    public void sendMessage() {

    }
}
