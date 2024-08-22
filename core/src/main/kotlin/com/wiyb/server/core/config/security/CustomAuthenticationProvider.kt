package com.wiyb.server.core.config.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationProvider
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.client.oidc.authentication.OidcAuthorizationCodeAuthenticationProvider
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomAuthenticationProvider(
    accessTokenResponseClient: OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>,
    oauthService: OAuth2UserService<OAuth2UserRequest, OAuth2User>,
    oidcService: OAuth2UserService<OidcUserRequest, OidcUser>
) : AuthenticationProvider {
    private val oauthProvider = OAuth2LoginAuthenticationProvider(accessTokenResponseClient, oauthService)
    private val oidcProvider = OidcAuthorizationCodeAuthenticationProvider(accessTokenResponseClient, oidcService)

    override fun authenticate(authentication: Authentication?): Authentication {
        // todo: authentication null인 경우 throw? return null? 디버깅 하면서 확인
        // todo: CustomWebAuthenticationProvider와 CustomMobileAuthenticationProvier로 나눠볼까?
        // todo: ProviderManager에서 어짜피 10~20개는 관리된다고 함

        val authorizationCodeAuthentication = authentication as OAuth2LoginAuthenticationToken
        return if (authorizationCodeAuthentication.clientRegistration.scopes.contains("openid")) {
            oidcProvider.authenticate(authentication)
        } else {
            oauthProvider.authenticate(authentication)
        }
    }

    override fun supports(authentication: Class<*>): Boolean = OAuth2LoginAuthenticationToken::class.java.isAssignableFrom(authentication)
}
