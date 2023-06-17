package io.github.gunkim.endpoint.http.room.advice

import io.github.gunkim.domain.exception.CommonException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CommonExceptionHandler {
    @ExceptionHandler(CommonException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun domainExceptionHandle(commonException: CommonException) = commonException.apply {
        CommonErrorResponse(
                code = errorCode.name,
                message = message
        )
    }
}
