package dongpb.agenticai.orchestratorservice.domain.tool;

import java.util.Map;

public interface Tool {
    String getType();
    String getDescription();
    Map<String, Object> execute(Map<String, Object> input);
}
