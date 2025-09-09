package dongpb.agenticai.orchestratorservice.domain.resource;

import dongpb.agenticai.orchestratorservice.database.entities.FunctionEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class HttpFunction extends Function{
    String url;
    String method;
    Map<String, Object> headers;
    Map<String, Object> requestParams;
    Map<String, Object> body;


    @Override
    public String getType() {
        return "http";
    }

    @Override
    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append("- ").append(this.getDescription()).append("\n");
        sb.append("url: ").append(url).append("\n");
        sb.append("method: ").append(method).append("\n");

        if (headers != null) {
            sb.append("headers: \n");
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                sb.append("\t").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        }

        if (requestParams != null) {
            sb.append("requestParams: \n");
            for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
                sb.append("\t").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        }

        if (body != null) {
            sb.append("body: \n");
            for (Map.Entry<String, Object> entry : body.entrySet()) {
                sb.append("\t").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        }

        return sb.toString();
    }

    @Override
    public FunctionEntity toEntity() {
        FunctionEntity entity = new FunctionEntity();
        entity.setFunctionId(this.getId());
        entity.setDescription(this.getDescription());
        entity.setResourceCode(this.getResourceCode());
        entity.setType(this.getType());

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("url", this.getUrl());
        metadata.put("method", this.getMethod());
        metadata.put("headers", this.getHeaders());
        metadata.put("requestParams", this.getRequestParams());
        metadata.put("body", this.getBody());
        entity.setMetadata(metadata);

        return entity;
    }
}
