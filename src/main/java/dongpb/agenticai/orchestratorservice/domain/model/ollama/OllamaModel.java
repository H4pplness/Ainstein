package dongpb.agenticai.orchestratorservice.domain.model.ollama;

import dongpb.agenticai.orchestratorservice.domain.model.AIModel;
import dongpb.agenticai.orchestratorservice.domain.model.AIRequest;
import dongpb.agenticai.orchestratorservice.domain.model.AIResponse;

public class OllamaModel implements AIModel {
    @Override
    public String getName() { return "ollama"; }

    @Override
    public AIResponse chat(AIRequest request) {
        return null;
    }
}
