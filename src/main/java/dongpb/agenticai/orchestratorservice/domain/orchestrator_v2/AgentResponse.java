package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dongpb.agenticai.orchestratorservice.common.JsonUtils;
import dongpb.agenticai.orchestratorservice.domain.agentic.Message;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.FunctionType;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.function_request.ExecuteToolRequest;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.function_request.FunctionRequest;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.function_request.ReturnResponseRequest;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.function_request.UseAgentRequest;
import lombok.Data;

import java.util.Map;

@Data
public class AgentResponse<T extends FunctionRequest> {
    String function;
    T response;

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T extends FunctionRequest> AgentResponse<T> fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        json = JsonUtils.extractJson(json);
        return mapper.readValue(json, mapper.getTypeFactory().constructParametricType(Message.class, clazz));
    }

    public static AgentResponse fromJson(String json) throws JsonProcessingException {
        json = JsonUtils.extractJson(json);
        Map<String, Object> map = JsonUtils.toMap(json);
        String function = map.get("function").toString();
        FunctionType type = FunctionType.getByName(function);

        switch (type) {
            case USE_AGENT :
                return fromJson(json, UseAgentRequest.class);
            case RETURN:
                return fromJson(json, ReturnResponseRequest.class);
            case EXECUTE_TOOL:
                return fromJson(json, ExecuteToolRequest.class);
            default:
                return null;
        }
    }
}
