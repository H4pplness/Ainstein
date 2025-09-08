package dongpb.agenticai.orchestratorservice.application.controller;

import dongpb.agenticai.orchestratorservice.domain.agentic.AgenticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/agentic")
public class AgenticController {
    private final AgenticService agenticService;

    @RequestMapping("/chat")
    public Object chat(@RequestParam(name = "request") String request) throws Exception {
        return agenticService.chat(request);
    }
}
