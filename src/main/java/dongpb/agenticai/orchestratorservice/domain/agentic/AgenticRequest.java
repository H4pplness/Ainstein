package dongpb.agenticai.orchestratorservice.domain.agentic;

import lombok.Data;

import java.util.List;

@Data
public class AgenticRequest {
    String request;
    List<String> resources;
    List<String> tools;
}
