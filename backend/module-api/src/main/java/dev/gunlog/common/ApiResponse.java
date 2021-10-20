package dev.gunlog.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ApiResponse<T> {
    private LocalDateTime requestTime = LocalDateTime.now();
    private String message;
    private int statusCode;
    private T data;

    public ApiResponse(String message, int statusCode, T data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<T>(message, 200, data);
    }
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<T>(message, 200, null);
    }
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>("SUCCESS!", 200, data);
    }
    public static <T> ApiResponse<T> failure(String message, int statusCode, T data) {
        return new ApiResponse<>(message, statusCode, data);
    }
    public static <T> ApiResponse<T> failure(String message, int statusCode) {
        return new ApiResponse<>(message, statusCode, null);
    }
    public static <T> ApiResponse<T> failure(int statusCode) {
        return new ApiResponse<>("ERROR!", statusCode, null);
    }
}