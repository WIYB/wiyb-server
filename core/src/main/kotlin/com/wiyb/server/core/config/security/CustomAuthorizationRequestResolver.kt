package com.wiyb.server.core.config.security

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest

class CustomAuthorizationRequestResolver(
    clientRegistrationRepository: ClientRegistrationRepository,
    authorizationRequestBaseUri: String
) : OAuth2AuthorizationRequestResolver {
    private val defaultAttrName =
        HttpSessionOAuth2AuthorizationRequestRepository::class.java.getName() + ".AUTHORIZATION_REQUEST"

    private val defaultResolver = DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, authorizationRequestBaseUri)

    override fun resolve(request: HttpServletRequest): OAuth2AuthorizationRequest? {
//        request?.session?.setAttribute(
//            "org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository.AUTHORIZATION_REQUEST",
//            OidcUserRequest()
//        )
//        val oauthRequest: OAuth2AuthorizationRequest = defaultResolver.resolve(request) ?: return null
        // todo: delete
//        request.setAttribute("device", "mobile")

        val registrationId = request.requestURI.split("/").last()
        val device: String? = request.getAttribute("device") as String?

        return if (device == "mobile") {
            resolve(request, registrationId)
        } else {
            defaultResolver.resolve(request)
        }
    }

    override fun resolve(
        request: HttpServletRequest,
        clientRegistrationId: String
    ): OAuth2AuthorizationRequest? {
        val oauthRequest: OAuth2AuthorizationRequest = defaultResolver.resolve(request, clientRegistrationId) ?: return null
        request.session.setAttribute(defaultAttrName, customResolve(oauthRequest))
        return null
    }

    private fun customResolve(oauthRequest: OAuth2AuthorizationRequest): OAuth2AuthorizationRequest =
        OAuth2AuthorizationRequest.from(oauthRequest).state("wiyb").build()
}
