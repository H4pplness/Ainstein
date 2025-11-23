package dongpb.agenticai.orchestratorservice.domain.orchestrator_v2.method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FunctionRegistry {
    private final Map<String, Function> functionMap = new HashMap<>();

    public FunctionRegistry(@Autowired List<Function> functions) {
        for (Function function : functions) {
            functionMap.put(function.getType().getName(), function);
        }
    }

    public Function getFunction(FunctionType functionType){
        return functionMap.get(functionType.getName());
    }

    public Function getFunctionByName(String name) {
        return functionMap.get(name);
    }
}
