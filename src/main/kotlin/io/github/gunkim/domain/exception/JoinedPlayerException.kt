package io.github.gunkim.domain.exception

class JoinedPlayerException(
    message: String,
    errorCode: ErrorCode = ErrorCode.JOINED_PLAYER,
) : CommonException(errorCode, message)
