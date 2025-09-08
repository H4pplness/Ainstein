package dongpb.agenticai.orchestratorservice.application.controller;

import dongpb.agenticai.orchestratorservice.domain.model.AIResponse;
import dongpb.agenticai.orchestratorservice.domain.orchestrator.OrchestratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orchestrator")
public class OrchestratorController {
    private final OrchestratorService orchestratorService;

    @GetMapping
    public Object chat(@RequestParam String request) {
        return orchestratorService.chat(request);
    }
}
