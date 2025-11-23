package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method;

import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.function_request.ReturnResponseRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReturnResponseFunction implements Function<ReturnResponseRequest> {
    @Override
    public FunctionType getType() {
        return FunctionType.RETURN;
    }

    @Override
    public Object execute(ReturnResponseRequest request) {
        return null;
    }

    @Override
    public String describe() {
        return "";
    }
}
