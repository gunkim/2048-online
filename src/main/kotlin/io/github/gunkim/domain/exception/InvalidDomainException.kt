package io.github.gunkim.domain.exception

class InvalidDomainException(
        override val message: String,
        errorCode: ErrorCode = ErrorCode.INVALID_DOMAIN
) : CommonException(errorCode, message)
