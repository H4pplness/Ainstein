package dongpb.agenticai.orchestratorservice.domain.resource;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.List;

@Data
public class Resource<T extends Function> {
    String resourceCode;

    String resourceName;
    String description;
    String type;

    List<T> functions;

    public String describe(){
        StringBuilder sb = new StringBuilder();
        sb.append(resourceName.toUpperCase()).append("\n\n");
        if (description != null) {
            sb.append("Mô tả : ").append(description).append("\n");
        }
        sb.append("Tool : ").append(type).append("\n");
        sb.append("Danh sách tài nguyên : ").append("\n");
        for (T function : functions) {
            sb.append(function.describe()).append("\n");
        }

        return sb.toString();
    }
}

