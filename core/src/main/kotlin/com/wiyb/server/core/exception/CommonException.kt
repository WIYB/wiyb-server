package com.wiyb.server.core.exception

import org.springframework.http.HttpStatus

open class CommonException(
    val errorCode: ErrorCode,
    override val message: String = errorCode.message
) : RuntimeException(message) {
    val httpStatus: HttpStatus = errorCode.httpStatus
    val code: Int = errorCode.code
}
