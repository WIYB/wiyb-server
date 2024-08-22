package com.wiyb.server.core.domain.auth.converter

import org.springframework.core.convert.converter.Converter
import org.springframework.http.RequestEntity
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter
import org.springframework.util.MultiValueMap

class CustomRequestEntityConverter : Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<*>> {
    private val defaultConverter = OAuth2AuthorizationCodeGrantRequestEntityConverter()

    override fun convert(request: OAuth2AuthorizationCodeGrantRequest): RequestEntity<*>? {
        val entity: RequestEntity<*> = defaultConverter.convert(request) ?: return null
        val registrationId = request.clientRegistration.registrationId

        @Suppress("UNCHECKED_CAST")
        val params: MultiValueMap<String, String> = entity.body as MultiValueMap<String, String>
        return null
    }
}
