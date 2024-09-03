package com.wiyb.server.core.domain.common

import com.wiyb.server.core.provider.TokenProvider
import org.springframework.http.ResponseCookie

class CustomCookie {
    companion object {
        lateinit var rootDomain: String

        fun make(
            key: String,
            value: String,
            path: String = "/",
            maxAge: Long? = null,
            httpOnly: Boolean = true,
            secure: Boolean = true,
            sameSite: String = "None"
        ): String {
            val dynamicAge: Long? =
                when (key) {
                    TokenProvider.ACCESS_TOKEN_KEY -> TokenProvider.ACCESS_TOKEN_EXPIRE_TIME / 1000L
                    TokenProvider.REFRESH_TOKEN_KEY -> TokenProvider.REFRESH_TOKEN_EXPIRE_TIME / 1000L
                    else -> maxAge
                }

            val cookie =
                ResponseCookie
                    .from(key, value)
                    .path(path)
                    .httpOnly(httpOnly)
                    .secure(secure)
                    .sameSite(sameSite)
                    .domain(rootDomain)

            if (dynamicAge != null) {
                cookie.maxAge(dynamicAge)
            }

            return cookie.build().toString()
        }
    }
}
