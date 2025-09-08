package dongpb.agenticai.orchestratorservice.domain.tool;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ToolRegistry {
    private final Map<String, Tool> tools = new ConcurrentHashMap<>();

    public ToolRegistry(List<Tool> toolList) {
        for (Tool t : toolList) tools.put(t.getType(), t);
    }

    public Tool get(String name) {
        Tool t = tools.get(name);
        if (t == null) throw new IllegalArgumentException("Unknown tool: " + name);
        return t;
    }

    public String describeAll() {
        return tools.values().stream()
                .map(Tool::getDescription)
                .collect(Collectors.joining("\n"));
    }

    public String describeChoseTools(List<String> toolNames) {
        return toolNames.stream()
                .map(name -> {
                    Tool t = tools.get(name);
                    if (t == null) {
                        throw new IllegalArgumentException("Unknown tool: " + name);
                    }
                    return t.getDescription();
                })
                .collect(Collectors.joining("\n"));
    }
}