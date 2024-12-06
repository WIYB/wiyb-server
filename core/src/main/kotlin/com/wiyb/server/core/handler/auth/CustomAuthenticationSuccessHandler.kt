package com.wiyb.server.core.handler.auth

import com.wiyb.server.core.domain.auth.CustomOAuth2UserDetails
import com.wiyb.server.core.domain.auth.TokenResponseWrapper
import com.wiyb.server.core.provider.TokenProvider
import com.wiyb.server.storage.database.entity.user.User
import com.wiyb.server.storage.database.entity.user.constant.Role
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.util.Base64
import java.util.UUID
import java.util.regex.Pattern

@Component
class CustomAuthenticationSuccessHandler(
    private val tokenProvider: TokenProvider,
    @Value("\${spring.config.origin.client}")
    private val clientOrigin: String
) : AuthenticationSuccessHandler {
    val base64Pattern: Pattern =
        Pattern.compile(
            "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$"
        )

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val user: User = (authentication.principal as CustomOAuth2UserDetails).user
        val sessionId: String = UUID.randomUUID().toString()
        val tokenDto = tokenProvider.generatePair(user, sessionId)
        val path = request.getAttribute("path") as String

        TokenResponseWrapper.addCookie(response, tokenDto)

        return response.sendRedirect(resolveUrl(user.role, path))
    }

    private fun resolveUrl(
        role: Role,
        path: String
    ): String {
        val baseUrl = "$clientOrigin/"
        val redirectPath: String =
            if (path.isNotBlank() && base64Pattern.matcher(path).matches()) {
                Base64.getDecoder().decode(path).toString(Charsets.UTF_8)
            } else {
                ""
            }

        return when (role) {
            Role.GUEST -> baseUrl.plus("sign?path=$path")
            else -> baseUrl.plus(redirectPath)
        }.replace(Regex("(?<!:)//+"), "/")
    }
}
