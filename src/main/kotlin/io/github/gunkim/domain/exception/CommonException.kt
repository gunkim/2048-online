package io.github.gunkim.domain.exception

abstract class CommonException(
    open val errorCode: ErrorCode,
    override val message: String,
) : Exception()
