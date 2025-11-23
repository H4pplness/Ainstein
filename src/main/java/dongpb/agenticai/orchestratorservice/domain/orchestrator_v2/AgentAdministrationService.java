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
import dongpb.agenticai.orchestratorservice.domain.redis.RedisService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final RedisService redisService;

    public Agent getAgentAndNearestChildren(Integer rootId) {
        List<AgentEntity> childrenEntities = agentRepository.findAllByParentAgentId(rootId);
        AgentEntity parentEntity = agentRepository.findById(rootId)
                .orElseThrow(() -> new BaseException(Errors.BAD_REQUEST));

        Agent agent = new Agent();
        agent.setAgentId(parentEntity.getAgentId());
        agent.setName(parentEntity.getName());
        agent.setDescription(parentEntity.getDescription());

        List<Agent> children = new ArrayList<>();
        for (AgentEntity childEntity : childrenEntities) {
            Agent childAgent = new Agent();
            childAgent.setName(childEntity.getName());
            childAgent.setDescription(childEntity.getDescription());

            children.add(childAgent);
        }

        agent.setChildren(children);


        return agent;
    }

    public Agent getAgentTree(Integer rootId) {
        String key = "AGENT_" + rootId;
        Object agentRedis = redisService.getData(key);
        if (agentRedis != null) {
            return (Agent) agentRedis;
        }

        List<AgentEntity> agentEntities = agentRepository.findAllAgentAndChildren(rootId);
        Agent agent = agentHelper.buildAgent(agentEntities,rootId);
        if (agent == null) {
            log.info("getAgent : List agents of business {} have some trouble",rootId);
            throw new BaseException(Errors.BAD_REQUEST);
        }

        redisService.saveData(key,agent);
        return agent;
    }

    @Transactional
    public String save(Agent agent) {
        List<AgentEntity> agentEntities = agentHelper.flattenAgent(agent);
        agentRepository.saveAll(agentEntities);

        AgentEntity rootAgent = agentEntities.get(0);
        String key = "AGENT_" + rootAgent.getAgentId();
        redisService.removeData(key);

        return "Success";
    }

    public String deleteById(Integer agentId) {
        agentRepository.deleteById(agentId);
        List<ToolResourceEntity> toolResourceEntities = toolResourceRepository.findByAgentId(agentId);
        toolResourceRepository.deleteAll(toolResourceEntities);

        return "Success";
    }
}
