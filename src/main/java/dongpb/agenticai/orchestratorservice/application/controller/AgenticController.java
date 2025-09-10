package dongpb.agenticai.orchestratorservice.application.controller;

import dongpb.agenticai.orchestratorservice.domain.agentic.AgenticPrompt;
import dongpb.agenticai.orchestratorservice.domain.agentic.AgenticRequest;
import dongpb.agenticai.orchestratorservice.domain.agentic.AgenticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/agentic")
public class AgenticController {
    private final AgenticService agenticService;

    @PostMapping("/chat")
    public Object chat(@RequestBody AgenticRequest request) throws Exception {
        return agenticService.chat(request);
    }
}
