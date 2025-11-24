package dongpb.agenticai.orchestratorservice.domain.conversation;

import dongpb.agenticai.orchestratorservice.application.exception.BaseException;
import dongpb.agenticai.orchestratorservice.application.exception.Errors;
import dongpb.agenticai.orchestratorservice.common.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


@Slf4j
public class AgentHandler implements EventHandler {
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
