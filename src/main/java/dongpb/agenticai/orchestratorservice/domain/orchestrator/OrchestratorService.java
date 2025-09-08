package dongpb.agenticai.orchestratorservice.domain.orchestrator;

import com.fasterxml.jackson.core.JsonProcessingException;
import dongpb.agenticai.orchestratorservice.common.JsonUtils;
import dongpb.agenticai.orchestratorservice.domain.model.AIRequest;
import dongpb.agenticai.orchestratorservice.domain.model.AIResponse;
import dongpb.agenticai.orchestratorservice.domain.model.AIService;
import dongpb.agenticai.orchestratorservice.domain.orchestrator.response.Message;
import dongpb.agenticai.orchestratorservice.domain.orchestrator.response.Plan;
import dongpb.agenticai.orchestratorservice.domain.orchestrator.response.SystemRequest;
import dongpb.agenticai.orchestratorservice.domain.orchestrator.response.ToolRequest;
import dongpb.agenticai.orchestratorservice.domain.tool.Tool;
import dongpb.agenticai.orchestratorservice.domain.tool.ToolRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrchestratorService {
    private final AIService aiService;
    private final ToolRegistry toolRegistry;

    public Object chat(String request) {
        AIRequest aiRequest = OrchestratorPrompt.getSimpleInitPrompt();

        AIRequest.Message userRequest = AIRequest.Message.builder()
                .role("user")
                .content(request)
                .build();

        aiRequest.addMessage(userRequest);
        AIResponse aiResponse = aiService.chat(aiRequest);

        Message firstResponse = null;
        try {
            firstResponse = Message.fromJson(aiResponse.getContent());
            if (firstResponse.getMethod().equals("plan")) {
                return handlePlan(aiRequest,firstResponse);
            }else {
                Message<String> returnMessage = (Message<String>) firstResponse;
                return returnMessage.getMessage();
            }
        } catch (JsonProcessingException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private Object handlePlan(AIRequest aiRequest,Message<Plan> planMessage) throws JsonProcessingException {
        String planMessageJson = planMessage.toJson();

        aiRequest.addMessage(getAssistantMessage(planMessageJson));

        Plan plan = planMessage.getMessage();
        for (Plan.Step step : plan.getSteps()) {
            String toolName = step.getTool();
            if (toolRegistry.get(toolName) == null) {
                throw new RuntimeException("Unknown tool: " + toolName);
            }

            // step 3
            SystemRequest systemRequest = new SystemRequest(step.getStep(),toolName);
            aiRequest.addMessage(getUserMessage(systemRequest.toMessage().toJson()));

            // send request to AI
            AIResponse aiResponse = aiService.chat(aiRequest);
            aiRequest.addMessage(getAssistantMessage(aiResponse.getContent()));

            // step 4
            Message<ToolRequest> aiSystemRequest = Message
                    .fromJson(aiResponse.getContent(),ToolRequest.class);

            Tool tool = toolRegistry.get(toolName);
            Map<String,Object> toolResponse = tool.execute(aiSystemRequest.getMessage().getInput());

            AIRequest.Message toolResponseMessage = getUserMessage(JsonUtils.toJson(toolResponse));
            aiRequest.addMessage(toolResponseMessage);

            aiResponse = aiService.chat(aiRequest);
            Message aiResponseMessage = Message.fromJson(aiResponse.getContent());
            if (aiResponseMessage.getMethod().equals("return")) {
                return aiResponseMessage.getMessage();
            }
            else {
                AIRequest.Message additionalMessage = getAssistantMessage(aiResponse.getContent());
                aiRequest.addMessage(additionalMessage);
            }
        }

        return null;
    }

    private AIRequest.Message getAssistantMessage(String message) {
        return AIRequest.Message
                .builder()
                .role("assistant")
                .content(message)
                .build();
    }

    private AIRequest.Message getUserMessage(String message){
        return AIRequest.Message
                .builder()
                .role("user")
                .content(message)
                .build();
    }
}
