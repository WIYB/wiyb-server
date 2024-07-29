package com.wiyb.server.core.handler.auth
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler

class CustomAuthenticationFailureHandler : AuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authenticationException: AuthenticationException
    ) {
        val referer: String =
            request.getHeader("Referer") ?: "${request.scheme}://${request.serverName}:${request.serverPort}"

        response.sendRedirect("$referer/sign/failure")
    }
}
