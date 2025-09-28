package dongpb.agenticai.orchestratorservice.application.controller;

import dongpb.agenticai.orchestratorservice.domain.conversation.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/conversation")
@RequiredArgsConstructor
public class ConversationController {
    private final ConversationService conversationService;

}
