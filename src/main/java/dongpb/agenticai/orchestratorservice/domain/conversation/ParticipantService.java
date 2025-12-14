package dongpb.agenticai.orchestratorservice.domain.conversation;

import dongpb.agenticai.orchestratorservice.application.exception.BaseException;
import dongpb.agenticai.orchestratorservice.application.exception.Errors;
import dongpb.agenticai.orchestratorservice.database.entities.ParticipantCodeEntity;
import dongpb.agenticai.orchestratorservice.database.repositories.ParticipantCodeRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final ParticipantCodeRepository participantCodeRepository;
    private final KafkaParticipantManager kafkaParticipantManager;
    private final EventHandlerRegistry eventHandlerRegistry;

    @PostConstruct
    public void init() {
        List<ParticipantCodeEntity> participantCodeEntities = participantCodeRepository.findAll();
        for (ParticipantCodeEntity p : participantCodeEntities) {
            kafkaParticipantManager.createParticipant(p.getParticipantCode(),eventHandlerRegistry.getHandler(AgentHandler.class));
        }
    }
    /**
     * create participant by id and participant type
     * @param id : reference id
     * @param participantType : person/agent
     * @return
     */
    public Object createParticipant(String id,ParticipantType participantType) {
        String code = participantType.getType() + "-" + id;

        Optional<ParticipantCodeEntity> participantCodeEntity = participantCodeRepository.findById(code);
        if (participantCodeEntity.isPresent()) {
            throw new BaseException(Errors.BAD_REQUEST);
        }

        ParticipantCodeEntity participantCode = new ParticipantCodeEntity();
        participantCode.setReferenceId(id);
        participantCode.setType(participantType.getType());
        participantCode.setParticipantCode(code);

        kafkaParticipantManager.createParticipant(code,eventHandlerRegistry.getHandler(AgentHandler.class));
        return participantCodeRepository.save(participantCode);
    }



}
