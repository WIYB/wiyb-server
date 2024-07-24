package com.wiyb.server.core.exception

class TokenException(
    errorCode: ErrorCode,
    message: String = errorCode.message
) : CommonException(errorCode, message)
