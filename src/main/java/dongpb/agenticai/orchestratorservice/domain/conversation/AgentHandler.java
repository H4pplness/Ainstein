package dongpb.agenticai.orchestratorservice.domain.conversation;

import com.fasterxml.jackson.databind.util.BeanUtil;
import dongpb.agenticai.orchestratorservice.application.exception.BaseException;
import dongpb.agenticai.orchestratorservice.application.exception.Errors;
import dongpb.agenticai.orchestratorservice.common.JsonUtils;
import dongpb.agenticai.orchestratorservice.database.entities.ConversationMessageEntity;
import dongpb.agenticai.orchestratorservice.database.repositories.ConversationMessageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class AgentHandler implements EventHandler {
    private final ConversationMessageRepository conversationMessageRepository;

    @Override
    public void handle(Object message) {
        try {
            Map<String,Object> mapObj = JsonUtils.toMap((String) message);
            Conversation.Message conversationMessage = JsonUtils.mapper.convertValue(mapObj,Conversation.Message.class);

            log.info("Sender {}",conversationMessage.getSenderCode());
            log.info("Receiver {}",conversationMessage.getReceiverCode());
            log.info("Content {}",conversationMessage.getContent());
        }
        catch (ClassCastException e) {
            log.error("Không thể cast {} ra kiểu Conversation.Message",message);
        }
        catch (Exception e) {
            throw new BaseException(Errors.CUSTOM_BAD_REQUEST,e.getMessage());
        }
    }

    @Override
    public String getCode() {
        return "";
    }
}
