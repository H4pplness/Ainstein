package dongpb.agenticai.orchestratorservice.application.controller;

import dongpb.agenticai.orchestratorservice.application.request.SendMessageRequest;
import dongpb.agenticai.orchestratorservice.domain.conversation.Conversation;
import dongpb.agenticai.orchestratorservice.domain.conversation.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/conversation")
@RequiredArgsConstructor
public class ConversationController {
    private final ConversationService conversationService;

    @PostMapping("/send-message")
    public Object sendMessage(@RequestBody SendMessageRequest request) {
        return conversationService.sendMessage(request.getConversationId(), request.getMessage());
    }

    @GetMapping("/{id}")
    public Conversation getConversation(@PathVariable("id") String conversationId) {
        return conversationService.getConversation(conversationId);
    }
}


