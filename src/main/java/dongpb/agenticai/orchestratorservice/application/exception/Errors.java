package dongpb.agenticai.orchestratorservice.application.exception;

import lombok.Getter;

@Getter
public enum Errors {
    BAD_REQUEST(400,"Bad request"),
    UNAUTHORIZED(401,"Unauthorized"),
    FORBIDDEN(403,"Forbidden"),
    NOT_FOUND(404,"Not found"),
    INTERNAL_SERVER_ERROR(500,"Internal server error"),

    NOT_FOUND_ENTITY(404,"Entity {} not found"),

    PAYLOAD_TOO_LARGE(413,"Payload too large"),

    MODEL_NOT_FOUND(404,"Model {} not found"),
    ;

    Errors(int statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
    }

    private final String message;
    private final int statusCode;
}
