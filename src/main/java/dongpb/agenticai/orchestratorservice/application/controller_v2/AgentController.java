package dongpb.agenticai.orchestratorservice.application.controller_v2;

import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.Agent;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.AgentAdministrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/agent")
@RequiredArgsConstructor
public class AgentController {
    private final AgentAdministrationService agentAdministrationService;

    @GetMapping("/{rootId}")
    public ResponseEntity<Agent> getAgent(@PathVariable Integer rootId) {
        return ResponseEntity.ok(agentAdministrationService.getAgent(rootId));
    }

    @PostMapping("")
    public ResponseEntity<String> createAgent(@RequestBody Agent agent) {
        return ResponseEntity.ok(agentAdministrationService.save(agent));
    }
}
