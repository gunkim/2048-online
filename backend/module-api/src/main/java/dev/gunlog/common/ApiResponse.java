package dev.gunlog.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {
    private String message;
    private int status;
    private T data;

    @Builder
    public ApiResponse(String message, int status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    private ApiResponse(final WebStatusCode code, final T errors) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.data = errors;
    }
    private ApiResponse(final WebStatusCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
    }
    public static <T> ApiResponse<T> success(T data) {
        return (ApiResponse<T>) ApiResponse.builder()
                .message(WebStatusCode.REQUEST_SUCCESS.getMessage())
                .status(WebStatusCode.REQUEST_SUCCESS.getStatus())
                .data(data)
                .build();
    }
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>();
    }
    public static ApiResponse of(final WebStatusCode code, final BindingResult bindingResult) {
        return new ApiResponse(code, FieldError.of(bindingResult));
    }

    public static ApiResponse of(final WebStatusCode code) {
        return new ApiResponse(code);
    }

    public static ApiResponse of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<ApiResponse.FieldError> errors = ApiResponse.FieldError.of(e.getName(), value, e.getErrorCode());
        return new ApiResponse(WebStatusCode.INVALID_TYPE_VALUE, errors);
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}