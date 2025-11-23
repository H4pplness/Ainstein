package dongpb.agenticai.orchestratorservice.application.controller_v2;

import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.Agent;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.AgentAdministrationService;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/agent")
@RequiredArgsConstructor
public class AgentController {
    private final AgentAdministrationService agentAdministrationService;
//    private final AgentService agentService;

    @GetMapping("/{rootId}")
    public ResponseEntity<Agent> getAgent(@PathVariable Integer rootId) {
        return ResponseEntity.ok(agentAdministrationService.getAgentTree(rootId));
    }

    @PostMapping("")
    public ResponseEntity<String> saveAgent(@RequestBody Agent agent) {
        return ResponseEntity.ok(agentAdministrationService.save(agent));
    }

    @DeleteMapping("/{agentId}")
    public ResponseEntity<String> deleteAgent(@PathVariable Integer agentId) {
        return ResponseEntity.ok(agentAdministrationService.deleteById(agentId));
    }
//
//    @PostMapping()
//    public ResponseEntity<Object> createConversation() {
//        return null;
//    }
//
//    @PostMapping()
//    public ResponseEntity<Object> chat(String conversationId) {
//        return null;
//    }
}
