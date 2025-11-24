package dongpb.agenticai.orchestratorservice.application.controller;

import dongpb.agenticai.orchestratorservice.application.request.CreateParticipantTopicRequest;
import dongpb.agenticai.orchestratorservice.application.request.SendMessageRequest;
import dongpb.agenticai.orchestratorservice.domain.conversation.KafkaParticipantManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class KafkaController {
    private final KafkaParticipantManager participantManager;

    @PostMapping("/create-participant")
    public void createParticipantTopic(@RequestBody CreateParticipantTopicRequest participant) {
        participantManager.createParticipant(participant.getCode(),null);
    }

    @PostMapping("/participant/{code}/send-event")
    public void sendMessage(@PathVariable String code, @RequestBody SendMessageRequest request) {
        participantManager.sendMessage(code,request.getMessage());
    }
}
