package dongpb.agenticai.orchestratorservice.domain.agents;

import lombok.Data;

import java.util.Map;

@Data
public abstract class Agent {
    String initPrompt;
    Map<String,Object> metadata;

    public abstract String getName();
    public abstract String execute(String input);
}
