package dongpb.agenticai.orchestratorservice.domain.tool;

import lombok.Data;


public enum ToolType {
    HTTP("http"),
    ;

    final String type;
    private ToolType(String type) {
        this.type = type;
    }
}
