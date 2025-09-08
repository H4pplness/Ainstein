package dongpb.agenticai.orchestratorservice.application.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException{
    private Errors errors;
    private String[] errorTemplate;

    public BaseException(Errors errors, String... errorTemplate) {
        super(errors.getMessage());
        this.errors = errors;
        this.errorTemplate = errorTemplate;
    }
}
