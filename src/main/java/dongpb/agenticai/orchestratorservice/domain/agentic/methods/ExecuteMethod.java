package dongpb.agenticai.orchestratorservice.domain.agentic.methods;

import lombok.Data;

import java.util.Map;

@Data
public class ExecuteMethod {
    String tool;
    Map<String, Object> input;
}
