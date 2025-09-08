package dongpb.agenticai.orchestratorservice.domain.tool;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ToolService {
    private final ToolRegistry toolRegistry;

    public Map<String, Object> execute(ToolRequest input) {
        if (toolRegistry.get(input.getTool()) == null) {
            throw new IllegalArgumentException("Unknown tool: " + input.getTool());
        }

        return toolRegistry.get(input.getTool()).execute(input.getContent());
    }
}
