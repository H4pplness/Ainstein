package dongpb.agenticai.orchestratorservice.domain.agentic;

import com.fasterxml.jackson.core.JsonProcessingException;
import dongpb.agenticai.orchestratorservice.application.exception.BaseException;
import dongpb.agenticai.orchestratorservice.application.exception.Errors;
import dongpb.agenticai.orchestratorservice.domain.model.AIRequest;
import dongpb.agenticai.orchestratorservice.domain.model.AIResponse;
import dongpb.agenticai.orchestratorservice.domain.model.AIService;
import dongpb.agenticai.orchestratorservice.domain.tool.Tool;
import dongpb.agenticai.orchestratorservice.domain.tool.ToolRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AgenticService {
    private final AIService aiService;
    private final ToolRegistry toolRegistry;

    public Object chat(String request) throws JsonProcessingException {
        AIRequest aiRequest = AgenticPrompt.getSimpleInitPrompt();
        aiRequest.addMessage("user",request);

        while (true){
            AIResponse aiResponse = aiService.chat(aiRequest);
            String content = aiResponse.getContent();
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

    Message<SystemResponseMethod> execute(String content) throws JsonProcessingException {
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
