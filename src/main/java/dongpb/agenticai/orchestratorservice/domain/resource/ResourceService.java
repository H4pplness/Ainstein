package dongpb.agenticai.orchestratorservice.domain.resource;

import dongpb.agenticai.orchestratorservice.database.entities.FunctionEntity;
import dongpb.agenticai.orchestratorservice.database.entities.ResourceEntity;
import dongpb.agenticai.orchestratorservice.database.repositories.FunctionRepository;
import dongpb.agenticai.orchestratorservice.database.repositories.ResourceRepository;
import jakarta.el.FunctionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;
    private final FunctionRepository functionRepository;

    public String create(Resource resource) {
        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setResourceCode(resource.resourceCode);
        resourceEntity.setResourceName(resource.resourceName);
        resourceEntity.setDescription(resource.description);
        resourceEntity.setType(resource.type);

        List<FunctionEntity> functionEntities = new ArrayList<>();

        List<Function> functions = resource.getFunctions();
        for (Function function : functions) {
            FunctionEntity functionEntity = function.toEntity();
            functionEntity.setResourceCode(resource.resourceCode);
            functionEntities.add(functionEntity);
        }

        resourceRepository.save(resourceEntity);
        functionRepository.saveAll(functionEntities);

        return "Successful !";
    }

    public Resource<Function> get(String resourceCode){
        ResourceEntity resourceEntity = resourceRepository.findByResourceCode(resourceCode)
                .orElseThrow(() -> new IllegalArgumentException("Unknown resource: " + resourceCode));

        Resource<Function> resource = new Resource<>();
        resource.resourceCode = resourceEntity.getResourceCode();
        resource.resourceName = resourceEntity.getResourceName();
        resource.description = resourceEntity.getDescription();
        resource.type = resourceEntity.getType();

        List<FunctionEntity> functionEntities = functionRepository.findByResourceCode(resourceCode);
        List<Function> functions = functionEntities.stream().map(Function::fromEntity).toList();
        resource.setFunctions(functions);

        return resource;
    }

    public String getDescription(String resourceCode) {
        Resource<Function> resource = get(resourceCode);
        return resource.describe();
    }

    public String update(String code,Resource resource){
        ResourceEntity resourceEntity = resourceRepository.findByResourceCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Unknown resource: " + code)) ;
        resourceEntity.setResourceName(resource.resourceName);
        resourceEntity.setDescription(resource.description);
        resourceEntity.setType(resource.type);

        List<FunctionEntity> functionEntities = new ArrayList<>();

        List<Function> functions = resource.getFunctions();
        for (Function function : functions) {
            FunctionEntity functionEntity = function.toEntity();
            functionEntity.setResourceCode(resource.resourceCode);
            functionEntities.add(functionEntity);
        }

        resourceRepository.save(resourceEntity);
        functionRepository.saveAll(functionEntities);

        return "Successful !";
    }

    public String delete(String resourceCode){
        ResourceEntity resourceEntity = resourceRepository.findByResourceCode(resourceCode)
                .orElseThrow(() -> new IllegalArgumentException("Unknown resource: " + resourceCode));

        List<FunctionEntity> functionEntities = functionRepository.findByResourceCode(resourceCode);

        resourceRepository.delete(resourceEntity);
        functionRepository.deleteAll(functionEntities);

        return "Successful !";
    }
}
