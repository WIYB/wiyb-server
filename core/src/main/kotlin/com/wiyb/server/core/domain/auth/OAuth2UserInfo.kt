package com.wiyb.server.core.domain.auth

import com.wiyb.server.storage.database.entity.auth.constant.SocialProvider
import jakarta.security.auth.message.AuthException

data class OAuth2UserInfo(
    val socialProvider: SocialProvider,
    val socialId: String,
    val name: String? = null,
    val email: String? = null
) {
    companion object {
        fun of(
            registrationId: String,
            attributes: MutableMap<String, Any>
        ): OAuth2UserInfo =
            when (registrationId) {
                "google" -> ofGoogle(attributes)
                "kakao" -> ofKakao(attributes)
                "naver" -> ofNaver(attributes)
                else -> throw AuthException("Not supported provider")
            }

        private fun ofGoogle(attributes: MutableMap<String, Any>): OAuth2UserInfo =
            OAuth2UserInfo(
                SocialProvider.GOOGLE,
                attributes["sub"] as String,
                attributes["name"] as String,
                attributes["email"] as String
            )

        private fun ofKakao(attributes: MutableMap<String, Any>): OAuth2UserInfo =
            OAuth2UserInfo(
                SocialProvider.KAKAO,
                (attributes["id"] as Long).toString(),
                (attributes["properties"] as MutableMap<*, *>)["nickname"]?.let { it as String },
                (attributes["kakao_account"] as MutableMap<*, *>)["email"]?.let { it as String }
            )

        private fun ofNaver(attributes: MutableMap<String, Any>): OAuth2UserInfo {
            val nested = attributes["response"] as MutableMap<*, *>
            return OAuth2UserInfo(
                SocialProvider.NAVER,
                nested["id"] as String,
                nested["name"]?.let { it as String },
                nested["email"]?.let { it as String }
            )
        }
    }
}
