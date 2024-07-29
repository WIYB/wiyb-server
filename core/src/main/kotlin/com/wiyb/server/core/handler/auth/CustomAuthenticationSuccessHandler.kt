package com.wiyb.server.core.handler.auth

import com.wiyb.server.core.domain.auth.CustomOAuth2UserDetails
import com.wiyb.server.core.domain.common.CustomCookie
import com.wiyb.server.core.provider.TokenProvider
import com.wiyb.server.storage.entity.User
import com.wiyb.server.storage.entity.constant.Role
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CustomAuthenticationSuccessHandler(
    private val tokenProvider: TokenProvider
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val referer: String =
            request.getHeader("Referer") ?: "${request.scheme}://${request.serverName}:${request.serverPort}"
        val user: User = (authentication.principal as CustomOAuth2UserDetails).user
        val sessionId: String = UUID.randomUUID().toString()
        val tokenDto = tokenProvider.generatePair(user, sessionId)

        response.addCookie(CustomCookie.makeForAccessToken(tokenDto.accessToken))
        response.addCookie(CustomCookie.makeForRefreshToken(tokenDto.refreshToken))

        when (user.role) {
            Role.GUEST -> response.sendRedirect("$referer/sign")
            else -> response.sendRedirect("$referer/main")
        }
    }
}
