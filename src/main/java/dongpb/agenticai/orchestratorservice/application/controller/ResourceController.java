package dongpb.agenticai.orchestratorservice.application.controller;

import dongpb.agenticai.orchestratorservice.domain.resource.Resource;
import dongpb.agenticai.orchestratorservice.domain.resource.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resource")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody Resource resource){
        return ResponseEntity.ok(resourceService.create(resource));
    }

    @GetMapping("/{code}")
    public ResponseEntity<Resource> get(@PathVariable(name = "code") String code){
        return ResponseEntity.ok(resourceService.get(code));
    }

    @GetMapping("/describe/{code}")
    public ResponseEntity<String> getDescribe(@PathVariable(name = "code") String code){
        return ResponseEntity.ok(resourceService.getDescription(code));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> delete(@PathVariable(name = "code") String code){
        return ResponseEntity.ok(resourceService.delete(code));
    }

    @PutMapping("/{code}")
    public ResponseEntity<String> update(@PathVariable(name = "code") String code,@RequestBody Resource resource){
        return ResponseEntity.ok(resourceService.update(code,resource));
    }
}
