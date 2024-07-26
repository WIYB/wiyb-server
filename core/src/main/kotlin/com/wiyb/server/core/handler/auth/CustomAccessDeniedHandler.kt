package com.wiyb.server.core.handler.auth

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class CustomAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        val exception = CommonException(ErrorCode.INSUFFICIENT_AUTHORITY)

        response.status = exception.getStatus()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"

        response.writer.write(exception.toJson())
        response.writer.flush()
    }
}
