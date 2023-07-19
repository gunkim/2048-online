package io.github.gunkim.endpoint.http.room.advice

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CommonExceptionHandler {
    @ExceptionHandler(value = [IllegalArgumentException::class, IllegalStateException::class])
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun illegalArgumentExceptionHandle(exception: RuntimeException) = CommonErrorResponse(
            message = exception.message ?: "invalid request",
    )
}
