package dongpb.agenticai.orchestratorservice.domain.agentic.methods;

import lombok.Data;

import java.util.Map;

@Data
public class SystemResponseMethod {
    String tool;
    Map<String, Object> output;
}
