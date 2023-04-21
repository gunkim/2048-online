package io.github.gunkim.game.domain.exception

abstract class CommonException(
    open val errorCode: ErrorCode,
) : Exception()
