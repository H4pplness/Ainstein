package dongpb.agenticai.orchestratorservice.domain.conversation;

import dongpb.agenticai.orchestratorservice.application.exception.BaseException;
import dongpb.agenticai.orchestratorservice.application.exception.Errors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EventHandlerRegistry {
    private Map<Class<? extends EventHandler>,EventHandler> eventHandlerMap;

    public EventHandlerRegistry(List<EventHandler> eventHandlers) {
        eventHandlerMap = new HashMap<>();
        for (EventHandler eventHandler : eventHandlers) {
            eventHandlerMap.put(eventHandler.getClass(),eventHandler);
        }
    }

    public EventHandler getHandler(Class clazz) {
        if (eventHandlerMap.containsKey(clazz)) {
            return eventHandlerMap.get(clazz);
        }
        log.error("[getHandler] : Không tìm thấy event handler");
        throw new BaseException(Errors.BAD_REQUEST);
    }
}
