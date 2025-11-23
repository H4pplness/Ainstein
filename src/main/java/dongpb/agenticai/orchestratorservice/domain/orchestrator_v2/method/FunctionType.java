package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum FunctionType {
    EXECUTE_TOOL("tool"),
    USE_AGENT("agent"),
    RETURN("return")
    ;

    private final String name;

    FunctionType(String name) {
        this.name = name;
    }

    public static FunctionType getByName(String name) {
        for (FunctionType type : FunctionType.values()) {
            if (Objects.equals(type.getName(), name)) {
                 return type;
            }
        }

        return null;
    }

}
