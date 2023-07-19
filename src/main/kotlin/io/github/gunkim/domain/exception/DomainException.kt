package io.github.gunkim.domain.exception

abstract class DomainException(
    open val errorCode: ErrorCode,
    override val message: String,
) : Exception()
