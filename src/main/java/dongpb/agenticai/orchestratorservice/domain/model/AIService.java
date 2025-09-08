package dongpb.agenticai.orchestratorservice.domain.model;

import dongpb.agenticai.orchestratorservice.application.exception.BaseException;
import dongpb.agenticai.orchestratorservice.application.exception.Errors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {
    private final Map<String, AIModel> modelMap;

    @Autowired
    public AIService(List<AIModel> aiModels) {
        modelMap = new HashMap<>();
        for (AIModel aiModel : aiModels) {
            modelMap.put(aiModel.getName(), aiModel);
        }
    }

    public AIResponse chat(AIRequest request) {
        String model = request.getModel();
        if (modelMap.containsKey(model)) {
            return modelMap.get(model).chat(request);
        } else {
            throw new BaseException(Errors.MODEL_NOT_FOUND, model);
        }
    }
}
