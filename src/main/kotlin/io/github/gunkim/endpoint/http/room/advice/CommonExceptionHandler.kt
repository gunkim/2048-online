package io.github.gunkim.endpoint.http.room.advice

import io.github.gunkim.domain.exception.DomainException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CommonExceptionHandler {
    @ExceptionHandler(DomainException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun domainExceptionHandle(domainException: DomainException) = domainException.apply {
        CommonErrorResponse(
            code = errorCode.name,
            message = message,
        )
    }
}
