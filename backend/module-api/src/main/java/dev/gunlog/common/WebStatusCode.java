package dev.gunlog.common;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WebStatusCode {
    // Common
    REQUEST_SUCCESS(200,"Request Sucess"),
    INVALID_INPUT_VALUE(400,"Invalid Input Value"),
    METHOD_NOT_ALLOWED(405,"Invalid Input Value"),
    ENTITY_NOT_FOUND(400,"Entity Not Found"),
    INTERNAL_SERVER_ERROR(500,"Server Error"),
    INVALID_TYPE_VALUE(400,"Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403,"Access is Denied"),
    // Auth
    INVALID_PASSWORD_VALUE(400, "Invalid Password Value"),
    JWT_EXPIRED_TOKEN(401, "Jwt Expired Token"),
    INVALID_AUTH_REQUEST(401, "Invalid Auth Request");

    private final String message;
    private int status;

    WebStatusCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }
    public int getStatus() {
        return status;
    }
}