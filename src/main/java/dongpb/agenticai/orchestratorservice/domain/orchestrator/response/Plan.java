package dongpb.agenticai.orchestratorservice.domain.orchestrator.response;

import lombok.Data;

import java.util.List;

@Data
public class Plan {
    List<Step> steps;

    @Data
    public static class Step {
        Integer step;
        String action;
        String tool;
    }

    public Step getStep(int index){
        return steps.get(index);
    }
}
