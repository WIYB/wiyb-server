package com.wiyb.server.core.handler.auth

import com.wiyb.server.core.domain.common.CustomCookie
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.Arrays
import java.util.Base64

class CustomAuthorizationRequestResolver(
    clientRegistrationRepository: ClientRegistrationRepository,
    authorizationRequestBaseUri: String
) : OAuth2AuthorizationRequestResolver {
    private val defaultResolver = DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, authorizationRequestBaseUri)

    override fun resolve(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        val authorizationRequest = defaultResolver.resolve(request)

        if (authorizationRequest != null) {
            makePathSession(request.getParameter("path"))
        } else {
            deletePathSession(resolvePath(request))
        }

        return authorizationRequest
    }

    override fun resolve(
        request: HttpServletRequest,
        clientRegistrationId: String?
    ): OAuth2AuthorizationRequest? = defaultResolver.resolve(request, clientRegistrationId)

    private fun makePathSession(path: String?) {
        if (path.isNullOrBlank()) return

        val attribute = RequestContextHolder.getRequestAttributes()
        val response = (attribute as ServletRequestAttributes).response

        response?.addHeader(
            HttpHeaders.SET_COOKIE,
            CustomCookie.make(
                key = "path",
                value = Base64.getUrlEncoder().encodeToString(path.toByteArray())
            )
        )
    }

    private fun deletePathSession(path: String?) {
        val attribute = RequestContextHolder.getRequestAttributes()
        val response = (attribute as ServletRequestAttributes).response

        attribute.setAttribute("path", path ?: "", RequestAttributes.SCOPE_REQUEST)

        response?.addHeader(
            HttpHeaders.SET_COOKIE,
            CustomCookie.make(
                key = "path",
                value = "",
                maxAge = 0
            )
        )
    }

    private fun resolvePath(request: HttpServletRequest): String? =
        Arrays
            .stream(request.cookies ?: arrayOf())
            .filter { it.name.equals("path") }
            .findAny()
            .map(Cookie::getValue)
            .orElse(null)
}
