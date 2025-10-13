package dongpb.agenticai.orchestratorservice.domain.agentic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dongpb.agenticai.orchestratorservice.common.JsonUtils;
import dongpb.agenticai.orchestratorservice.domain.agentic.methods.ExecuteMethod;
import dongpb.agenticai.orchestratorservice.domain.agentic.methods.PlanMethod;
import dongpb.agenticai.orchestratorservice.domain.agentic.methods.ThinkMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message<T> {
    private String role;
    private String method;
    private T message;

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> Message<T> fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        json = JsonUtils.extractJson(json);
        return mapper.readValue(json, mapper.getTypeFactory().constructParametricType(Message.class, clazz));
    }

    public static Message fromJson(String json) throws JsonProcessingException {
        json = JsonUtils.extractJson(json);
        Map<String, Object> map = JsonUtils.toMap(json);
        String method = map.get("method").toString();
        switch (method){
            case "think" :
                return fromJson(json, ThinkMethod.class);
            case "return" :
                return fromJson(json, String.class);
            case "execute" :
                return fromJson(json, ExecuteMethod.class);
            case "plan" :
                return fromJson(json, PlanMethod.class);
            default:
                return fromJson(json, Object.class);
        }
    }

    public String toJson() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }

//    public static void main(String[] args) throws JsonProcessingException {
//        String json = """
//                {
//            	"role" : "assistant",
//            	"method" : "plan",
//            	"message" : {
//            		"steps" : [
//            			{
//            				"step" : 1,
//            				"action" : "<Tên hành động 1>",
//            				"tool" : "<Tên của tool cần sử dụng>"
//            			},
//            			{
//            				"step" : 2,
//                			"action" : "<Tên hành động 2>",
//            	    		"tool" : "<Tên của tool cần sử dụng>"
//            	        }
//            		]
//            	}
//            }
//            """;
//        Message<Plan> message = Message.fromJson(json, Plan.class);
//
//        System.out.println(message.toJson());
//    }
}
