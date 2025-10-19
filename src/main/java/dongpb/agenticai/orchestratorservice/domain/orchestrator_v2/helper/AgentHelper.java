package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.helper;

import dongpb.agenticai.orchestratorservice.application.exception.BaseException;
import dongpb.agenticai.orchestratorservice.application.exception.Errors;
import dongpb.agenticai.orchestratorservice.database.entities.agent.AgentEntity;
import dongpb.agenticai.orchestratorservice.database.repositories.AgentRepository;
import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.Agent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentHelper {
    private final AgentRepository agentRepository;
    /**
     * build agent with agentEntities from database
     * @param agentEntities
     * @param rootId
     * @return
     */
    public Agent buildAgent(List<AgentEntity> agentEntities,Integer rootId) {
        if (agentEntities == null || agentEntities.isEmpty()) {
            log.error("buildAgent : agentEntities is null or empty");
            return null;
        }
        Map<Integer, Agent> agentNode = new HashMap<>();
        for (AgentEntity agentEntity : agentEntities) {
            agentNode.put(agentEntity.getAgentId(),Agent.builder()
                            .agentId(agentEntity.getAgentId())
                            .name(agentEntity.getName())
                            .description(agentEntity.getDescription())
                            .build());
        }

        Agent root = agentNode.get(rootId);
        for (AgentEntity agentEntity : agentEntities) {
            if (Objects.equals(agentEntity.getAgentId(), rootId)) continue;
            Agent agent = agentNode.get(agentEntity.getAgentId());
            if (agentEntity.getParentAgentId() == null) continue;
            Agent parentAgent = agentNode.get(agentEntity.getParentAgentId());
            if (parentAgent == null) {
                log.error("buildAgent : agent which id is {} not exist",agentEntity.getParentAgentId());
                return null;
            }
            parentAgent.addChild(agent);
        }

        if (root == null) {
            log.error("buildAgent : not found root agent");
            return null;
        }
        Set<Integer> nodeIdSet = new HashSet<>();
        if (hasCycle(root,nodeIdSet)) {
            log.error("buildAgent : tree has a cycle");
            return null;
        }

        return root;
    }

    private boolean hasCycle(Agent node,Set<Integer> nodeIdSet) {
        if (nodeIdSet.contains(node.getAgentId())) {
            return true;
        }
        if (node.getChildren() == null || node.getChildren().isEmpty()){
            return false;
        }

        for (Agent child : node.getChildren()) {
            if (hasCycle(child, nodeIdSet)) {
                return true;
            }
        }

        return false;
    }

    public List<AgentEntity> flattenAgent(Agent agent) {
        List<AgentEntity> flattenAgents = new ArrayList<>();
        Set<Integer> idSet = new HashSet<>();
        AgentEntity rootAgentEntity = null;
        if (agent.getAgentId() != null) {
            log.error("convertToListAgentEntities : Not found agent which id is {}",agent.getAgentId());
            rootAgentEntity = agentRepository.findById(agent.getAgentId())
                    .orElseThrow(()->new BaseException(Errors.NOT_FOUND));
        }
        getFlattenAgents(agent,flattenAgents,idSet, rootAgentEntity == null ? null : rootAgentEntity.getParentAgentId());
        return flattenAgents;
    }

    private void getFlattenAgents(Agent agent, List<AgentEntity> agents, Set<Integer> idSet, Integer parentId) {
        if (idSet.contains(agent.getAgentId())) {
            log.error("getFlattenAgents : agent {} was created",agent.getAgentId());
            throw new BaseException(Errors.BAD_REQUEST);
        };
        if (agent.getAgentId() != null) {
            idSet.add(agent.getAgentId());
        }
        AgentEntity agentEntity = agentToAgentEntity(agent,parentId);
        if (agentEntity.getAgentId() == null) {
            agentRepository.save(agentEntity);
        }

        agents.add(agentEntity);
        if (agent.getChildren() == null || agent.getChildren().isEmpty()){
            return;
        }
        for (Agent child : agent.getChildren()) {
            getFlattenAgents(child,agents,idSet, agentEntity.getAgentId());
        }
    }

    private AgentEntity agentToAgentEntity(Agent agent,Integer parentId) {
        AgentEntity agentEntity = new AgentEntity();
        if (agent.getAgentId() != null) {
            agentEntity.setAgentId(agent.getAgentId());
        }
        agentEntity.setName(agent.getName());
        agentEntity.setDescription(agent.getDescription());
        if (agent.getModel() != null) {
            agentEntity.setModelId(agent.getModel().getModelId());
        }
        if (parentId != null) {
            agentEntity.setParentAgentId(parentId);
        }
        return agentEntity;
    }

}
