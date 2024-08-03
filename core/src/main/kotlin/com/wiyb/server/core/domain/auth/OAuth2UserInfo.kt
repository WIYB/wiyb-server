package com.wiyb.server.core.domain.auth

import com.wiyb.server.storage.database.entity.auth.constant.SocialProvider
import jakarta.security.auth.message.AuthException

data class OAuth2UserInfo(
    val socialProvider: SocialProvider,
    val socialId: String,
    val name: String,
    val email: String
) {
    companion object {
        fun of(
            registrationId: String,
            attributes: MutableMap<String, Any>
        ): OAuth2UserInfo =
            when (registrationId) {
                "google" -> ofGoogle(attributes)
                else -> throw AuthException("Not supported provider")
            }

        private fun ofGoogle(attributes: MutableMap<String, Any>): OAuth2UserInfo =
            OAuth2UserInfo(
                SocialProvider.GOOGLE,
                attributes["sub"] as String,
                attributes["name"] as String,
                attributes["email"] as String
            )
    }
}
