package dongpb.agenticai.orchestratorservice.domain.agentic;

import lombok.Data;

import java.util.Map;

@Data
public class ExecuteMethod {
    String tool;
    Map<String, Object> input;
}
