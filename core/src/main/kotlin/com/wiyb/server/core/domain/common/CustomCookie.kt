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
            httpOnly: Boolean = true,
            secure: Boolean = true,
            sameSite: String = "None"
        ): String {
            val maxAge =
                if (key == TokenProvider.REFRESH_TOKEN_KEY) {
                    TokenProvider.REFRESH_TOKEN_EXPIRE_TIME / 1000L
                } else {
                    TokenProvider.ACCESS_TOKEN_EXPIRE_TIME / 1000L
                }

            return ResponseCookie
                .from(key, value)
                .path(path)
                .maxAge(maxAge)
                .httpOnly(httpOnly)
                .secure(secure)
                .sameSite(sameSite)
                .domain(rootDomain)
                .build()
                .toString()
        }
    }
}
