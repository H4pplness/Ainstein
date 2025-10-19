package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2;

import lombok.Data;

import java.util.List;

@Data
public class Conservation {
    String conservationId;
    List<Message> messages;

    @Data
    public static class Message {
        String sender;
        Object content;
    }
}
