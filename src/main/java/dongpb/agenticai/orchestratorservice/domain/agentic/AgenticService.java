package dongpb.agenticai.orchestratorservice.domain.agentic;

import com.fasterxml.jackson.core.JsonProcessingException;
import dongpb.agenticai.orchestratorservice.application.exception.BaseException;
import dongpb.agenticai.orchestratorservice.application.exception.Errors;
import dongpb.agenticai.orchestratorservice.domain.model.AIRequest;
import dongpb.agenticai.orchestratorservice.domain.model.AIResponse;
import dongpb.agenticai.orchestratorservice.domain.model.AIService;
import dongpb.agenticai.orchestratorservice.domain.resource.Function;
import dongpb.agenticai.orchestratorservice.domain.resource.Resource;
import dongpb.agenticai.orchestratorservice.domain.resource.ResourceService;
import dongpb.agenticai.orchestratorservice.domain.tool.Tool;
import dongpb.agenticai.orchestratorservice.domain.tool.ToolRegistry;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AgenticService {
    private final AIService aiService;
    private final ToolRegistry toolRegistry;
    private final ResourceService resourceService;

    public Object chat(AgenticRequest request) throws JsonProcessingException {
        List<String> resources = request.getResources().stream().map(resourceService::getDescription).toList();
        List<Tool> tools = request.getTools().stream().map(toolRegistry::get).toList();

        AIRequest aiRequest = AgenticPrompt.getInitPrompt(tools,resources);
        aiRequest.addMessage("user",Message.builder().role("user").message(request.getRequest()).build().toJson());

        while (true){
            AIResponse aiResponse = aiService.chat(aiRequest);
            String content = aiResponse.getContent();
            if(content.isEmpty()) {
                throw new BaseException(Errors.BAD_REQUEST,"The context is overwhelming!");
            }
            Message message = Message.fromJson(content);
            aiRequest.addMessage("assistant",content);
            switch (message.getMethod()){
                case "think" :
                    break;
                case "return" :
                    return message.getMessage();
                case "execute" :
                    aiRequest.addMessage("user",execute(content).toJson());
                    break;
                default:
                    throw new BaseException(Errors.BAD_REQUEST);
            }
        }
    }

    private Message<SystemResponseMethod> execute(String content) throws JsonProcessingException {
        Message<ExecuteMethod> executeMethodMessage = Message.fromJson(content, ExecuteMethod.class);
        String toolName = executeMethodMessage.getMessage().getTool();
        Tool tool = toolRegistry.get(toolName);
        if (tool == null) {
            throw new BaseException(Errors.BAD_REQUEST,"Unknown tool: " + toolName);
        }

        Map<String,Object> output = tool.execute(executeMethodMessage.getMessage().getInput());

        SystemResponseMethod systemResponseMethod = new SystemResponseMethod();
        systemResponseMethod.setTool(toolName);
        systemResponseMethod.setOutput(output);

        Message<SystemResponseMethod> systemResponseMethodMessage = new Message<>();
        systemResponseMethodMessage.setMethod("response");
        systemResponseMethodMessage.setMessage(systemResponseMethod);

        return systemResponseMethodMessage;
    }
}
