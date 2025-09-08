package dongpb.agenticai.orchestratorservice.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class AIRequest {
    private String model;
    private List<Message> messages = new ArrayList<>();
    private Map<String, Object> metadata;

    @Data
    @Builder
    public static class Message{
        private String role;
        private String content;
    }

    public void addMessage(Message message){
        this.messages.add(message);
    }

    public void addMessage(String role,String content) {
        this.messages.add(Message.builder().role(role).content(content).build());
    }

    public void addMessages(List<Message> messages){
        this.messages.addAll(messages);
    }
}
