package dongpb.agenticai.orchestratorservice.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class JsonUtils {
    public static String extractJson(String text) {
        int first = text.indexOf('{');
        int last = text.lastIndexOf('}');
        if (first >= 0 && last > first) return text.substring(first, last + 1);
        return text;
    }

    public static Map<String,Object> toMap(String text) {
        text = extractJson(text);
        log.info("Json : {}", text);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(text, Map.class);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String toJson(Object object){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
