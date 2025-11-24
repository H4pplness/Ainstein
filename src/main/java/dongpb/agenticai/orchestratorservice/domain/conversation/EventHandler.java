package dongpb.agenticai.orchestratorservice.domain.conversation;

public interface EventHandler {
    void handle(Object message);
    String getCode();
}
