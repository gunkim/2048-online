package io.github.gunkim.domain.exception

import io.github.gunkim.domain.exception.CommonException
import io.github.gunkim.domain.exception.ErrorCode

class JoinedPlayerException(errorCode: ErrorCode = ErrorCode.JOINED_PLAYER) : CommonException(errorCode)
