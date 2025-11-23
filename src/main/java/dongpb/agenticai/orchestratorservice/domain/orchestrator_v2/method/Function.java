package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method;

import dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method.function_request.FunctionRequest;

public interface Function<T extends FunctionRequest> {
    FunctionType getType();
    Object execute(T request);
    String describe();
}
