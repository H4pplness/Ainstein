package dongpb.agenticai.orchestratorservice.domain.agentic;

import lombok.Data;

import java.util.Map;

@Data
public class SystemResponseMethod {
    String tool;
    Map<String, Object> output;
}
