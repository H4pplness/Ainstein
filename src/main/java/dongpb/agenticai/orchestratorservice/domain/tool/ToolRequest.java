package dongpb.agenticai.orchestratorservice.domain.tool;

import lombok.Data;

import java.util.Map;

@Data
public class ToolRequest {
    String tool;
    Map<String, Object> content;
}
