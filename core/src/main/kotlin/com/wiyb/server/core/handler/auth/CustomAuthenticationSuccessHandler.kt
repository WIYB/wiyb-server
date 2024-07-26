package com.wiyb.server.core.handler.auth

import com.wiyb.server.core.domain.auth.CustomOAuth2UserDetails
import com.wiyb.server.core.domain.auth.TokenResponseWrapper
import com.wiyb.server.core.provider.TokenProvider
import com.wiyb.server.storage.entity.User
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
        val user: User = (authentication.principal as CustomOAuth2UserDetails).user
        val sessionId: String = UUID.randomUUID().toString()
        val tokenDto = tokenProvider.generatePair(user, sessionId)

        TokenResponseWrapper(response).send(tokenDto)
    }
}
