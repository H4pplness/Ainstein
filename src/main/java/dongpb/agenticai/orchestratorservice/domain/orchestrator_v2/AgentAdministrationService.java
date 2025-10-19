package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2;

import dongpb.agenticai.orchestratorservice.application.exception.BaseException;
import dongpb.agenticai.orchestratorservice.application.exception.Errors;
import dongpb.agenticai.orchestratorservice.database.entities.agent.AgentEntity;
import dongpb.agenticai.orchestratorservice.database.entities.agent.ToolResourceEntity;
import dongpb.agenticai.orchestratorservice.database.repositories.AgentRepository;
import dongpb.agenticai.orchestratorservice.database.repositories.BusinessRepository;
import dongpb.agenticai.orchestratorservice.database.repositories.ModelRepository;
import dongpb.agenticai.orchestratorservice.database.repositories.ToolResourceRepository;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.helper.AgentHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentAdministrationService {
    private final BusinessRepository businessRepository;
    private final AgentRepository agentRepository;
    private final ModelRepository modelRepository;
    private final ToolResourceRepository toolResourceRepository;
    private final AgentHelper agentHelper;

    public Agent getAgent(Integer rootId) {
        List<AgentEntity> agentEntities = agentRepository.findAllByBusinessId(rootId);
        Agent agent = agentHelper.buildAgent(agentEntities,rootId);
        if (agent == null) {
            log.info("getAgent : List agents of business {} have some trouble",rootId);
            throw new BaseException(Errors.BAD_REQUEST);
        }

        return agent;
    }

    @Transactional
    public String save(Agent agent) {
        List<AgentEntity> agentEntities = agentHelper.flattenAgent(agent);
        agentRepository.saveAll(agentEntities);

        return "Success";
    }

    public void deleteById(Integer agentId) {
        agentRepository.deleteById(agentId);
        List<ToolResourceEntity> toolResourceEntities = toolResourceRepository.findByAgentId(agentId);
        toolResourceRepository.deleteAll(toolResourceEntities);
    }
}
