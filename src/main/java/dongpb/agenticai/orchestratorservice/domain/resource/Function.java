package dongpb.agenticai.orchestratorservice.domain.resource;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dongpb.agenticai.orchestratorservice.application.exception.BaseException;
import dongpb.agenticai.orchestratorservice.application.exception.Errors;
import dongpb.agenticai.orchestratorservice.database.entities.FunctionEntity;
import lombok.Data;

import java.util.Map;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HttpFunction.class, name = "http")
})
public abstract class Function {
    private Long id;
    private String type;
    private String description;
    private String resourceCode;

    public abstract String getType();
    public abstract String describe();
    public abstract FunctionEntity toEntity();

    public static Function fromEntity(FunctionEntity entity) {
        switch (entity.getType()){
            case "http":
                HttpFunction httpFunction = new HttpFunction();
                httpFunction.setId(entity.getFunctionId());
                httpFunction.setType(entity.getType());
                httpFunction.setDescription(entity.getDescription());
                httpFunction.setResourceCode(entity.getResourceCode());

                Map<String, Object> metadata = entity.getMetadata();
                httpFunction.setUrl((String) metadata.get("url"));
                httpFunction.setMethod((String) metadata.get("method"));
                httpFunction.setHeaders((Map<String, Object>) metadata.get("headers"));
                httpFunction.setRequestParams((Map<String, Object>) metadata.get("requestParams"));
                httpFunction.setBody((Map<String, Object>) metadata.get("body"));

                return httpFunction;
            default:
                throw new BaseException(Errors.BAD_REQUEST);
        }
    }
}
