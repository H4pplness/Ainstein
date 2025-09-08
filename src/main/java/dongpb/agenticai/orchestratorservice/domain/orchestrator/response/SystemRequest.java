package dongpb.agenticai.orchestratorservice.domain.orchestrator.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SystemRequest {
    Integer step;
    String tool;

    public Message toMessage(){
        return Message.builder()
                .role("system")
                .message("request")
                .message(this)
                .build();
    }
}
