package dongpb.agenticai.orchestratorservice.application.controller;

import dongpb.agenticai.orchestratorservice.application.request.CreateParticipantRequest;
import dongpb.agenticai.orchestratorservice.domain.conversation.ParticipantService;
import dongpb.agenticai.orchestratorservice.domain.conversation.ParticipantType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/v1/participants")
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;

    @GetMapping("/{participantCode}")
    public Object getParticipantById(@PathVariable String participantCode) {
        return null;
    }

    @PostMapping("")
    public Object createParticipant(@RequestBody CreateParticipantRequest request) {
        ParticipantType type = ParticipantType.fromType(request.getType());
        if (type == null) return null;
        return participantService.createParticipant(request.getReferenceId(), type);
    }

}
