package dongpb.agenticai.orchestratorservice.domain.agents;

import dongpb.agenticai.orchestratorservice.domain.model.AIRequest;
import lombok.Data;

@Data
public class SimpleAgent extends Agent{

    @Override
    public String getName() {
        return "simple-agent";
    }

    @Override
    public String execute(String input) {
        AIRequest request = new AIRequest();
        request.setModel("groq");
        return "";
    }
}
