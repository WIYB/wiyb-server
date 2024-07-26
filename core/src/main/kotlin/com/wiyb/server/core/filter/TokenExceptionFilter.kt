package com.wiyb.server.core.filter

import com.wiyb.server.core.domain.exception.CommonException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter

class TokenExceptionFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            val exception = if (e is CommonException) e else CommonException.fromInternalException(e)

            response.status = exception.getStatus()
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.characterEncoding = "UTF-8"

            response.writer.write(exception.toJson())
            response.writer.flush()
        }
    }
}
