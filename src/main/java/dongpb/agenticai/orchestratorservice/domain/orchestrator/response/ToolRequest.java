package dongpb.agenticai.orchestratorservice.domain.orchestrator.response;

import lombok.Data;

import java.util.Map;

@Data
public class ToolRequest {
    Integer step;
    String tool;
    Map<String,Object> input; // Nội dung gửi đi
}
