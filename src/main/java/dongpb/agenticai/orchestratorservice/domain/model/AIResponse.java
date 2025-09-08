package dongpb.agenticai.orchestratorservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AIResponse {
    private String content;
    private Map<String, Object> metadata;
}
