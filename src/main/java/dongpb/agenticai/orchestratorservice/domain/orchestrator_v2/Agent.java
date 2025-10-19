package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2;

import dongpb.agenticai.orchestratorservice.domain.model.AIModel;
import dongpb.agenticai.orchestratorservice.domain.tool.Tool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Agent {
    Integer agentId;
    String name;
    String description;
    Model model;

    List<Tool> tools;

    @Builder.Default
    List<Agent> children = new ArrayList<>();

    public void addChild(Agent agent){
        children.add(agent);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Model {
        Integer modelId;
        String name;
        AIModel aiModel;
    }

}
