package dongpb.agenticai.orchestratorservice.domain.model;

public interface AIModel {
    String getName();
    AIResponse chat(AIRequest request);
}
