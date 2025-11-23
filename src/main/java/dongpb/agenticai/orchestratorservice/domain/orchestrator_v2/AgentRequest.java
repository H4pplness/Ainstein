package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dongpb.agenticai.orchestratorservice.common.JsonUtils;
import lombok.Data;
import java.util.Map;

@Data
public class AgentRequest {
    private String conversationId;
    private Integer agentId;
    private SenderRequest request;
    private Map<String,Object> metadata;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Data
    public static class SenderRequest {
        String sender;
        Map<String,Object> content;

        public String toJson() throws JsonProcessingException {
            return mapper.writeValueAsString(this);
        }

        public static SenderRequest fromJson(String json) throws JsonProcessingException {
            json = JsonUtils.extractJson(json);
            return mapper.readValue(json,SenderRequest.class);
        }
    }
}
