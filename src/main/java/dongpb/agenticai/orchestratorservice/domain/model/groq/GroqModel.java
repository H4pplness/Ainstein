package dongpb.agenticai.orchestratorservice.domain.model.groq;


import dongpb.agenticai.orchestratorservice.application.exception.BaseException;
import dongpb.agenticai.orchestratorservice.application.exception.Errors;
import dongpb.agenticai.orchestratorservice.domain.model.AIModel;
import dongpb.agenticai.orchestratorservice.domain.model.AIRequest;
import dongpb.agenticai.orchestratorservice.domain.model.AIResponse;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Setter
public class GroqModel implements AIModel {
    @Value("${groq.secret.api-key}")
    private String apiKey;

    @Value("${groq.url}")
    private String url;

    private final RestTemplate restTemplate;

    @Override
    public String getName() {
        return "groq";
    }

    @Override
    public AIResponse chat(AIRequest request) {
        GroqRequest groqRequest = convertToGroqRequest(request);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + apiKey);
        httpHeaders.set("Content-Type", "application/json");

        HttpEntity<GroqRequest> entity = new HttpEntity<>(groqRequest, httpHeaders);

        try {
            ResponseEntity<GroqResponse> response = restTemplate.postForEntity(
                    url, entity, GroqResponse.class
            );
            return convertToAIResponse(response.getBody());
        } catch (Exception e) {
            throw new BaseException(Errors.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private GroqRequest convertToGroqRequest(AIRequest request) {

        List<GroqMessageRequest> groqMessageRequests = request.getMessages().stream()
                .map(aiMessage->new GroqMessageRequest(aiMessage.getRole(),aiMessage.getContent())).toList();

        return GroqRequest.builder()
                .model("gemma2-9b-it")
                .messages(groqMessageRequests)
                .build();
    }

    private AIResponse convertToAIResponse(GroqResponse groqResponse) {
        Map<String,Object> metadata = new HashMap<>();
        metadata.put("model", "groq");
        metadata.put("created",groqResponse.getCreated());
        return AIResponse.builder()
                .content(groqResponse.getChoices().get(0).getMessage().getContent())
                .metadata(metadata)
                .build();
    }


    @Data
    @Builder
    public static class GroqRequest {
        private String model;
        private List<GroqMessageRequest> messages;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GroqMessageRequest{
        private String role;
        private String content;
    }


    @Data
    @Builder
    public static class GroqResponse {
        private String id;
        private String object;
        private long created;
        private String model;
        private List<Choice> choices;
        private Usage usage;
        private Object usage_breakdown;
        private String system_fingerprint;
        private XGroq x_groq;
        private String service_tier;

        @Data
        public static class Choice {
            private int index;
            private GroqMessageRequest message;
            private Object logprobs;
            private String finish_reason;
        }

        @Data
        public static class Usage {
            private double queue_time;
            private int prompt_tokens;
            private double prompt_time;
            private int completion_tokens;
            private double completion_time;
            private int total_tokens;
            private double total_time;
        }

        @Data
        public static class XGroq {
            private String id;
        }
    }
}
