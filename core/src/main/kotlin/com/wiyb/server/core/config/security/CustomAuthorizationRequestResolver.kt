package com.wiyb.server.core.config.security

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest

class CustomAuthorizationRequestResolver(
    clientRegistrationRepository: ClientRegistrationRepository,
    authorizationRequestBaseUri: String
) : OAuth2AuthorizationRequestResolver {
    private val defaultResolver = DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, authorizationRequestBaseUri)

    override fun resolve(request: HttpServletRequest?): OAuth2AuthorizationRequest? = resolveInternal(request)

    override fun resolve(
        request: HttpServletRequest?,
        clientRegistrationId: String?
    ): OAuth2AuthorizationRequest? = defaultResolver.resolve(request, clientRegistrationId)

    private fun resolveInternal(request: HttpServletRequest?): OAuth2AuthorizationRequest? {
        val provider = request?.getParameter("provider")

        return if (provider != null) {
            defaultResolver.resolve(request, provider.lowercase())
        } else {
            null
        }
    }
}
