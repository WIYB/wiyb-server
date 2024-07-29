package com.wiyb.server.core.domain.common

import org.springframework.http.ResponseCookie

class CustomResponseCookie {
    companion object {
        fun make(
            name: String,
            value: String,
            path: String = "/",
            httpOnly: Boolean = true,
            secure: Boolean = true,
            sameSite: String = "None"
        ) = ResponseCookie
            .from(name, value)
            .path(path)
            .httpOnly(httpOnly)
            .secure(secure)
            .sameSite(sameSite)
            .build()

        fun makeForAccessToken(value: String): ResponseCookie = make("access", value)

        fun makeForRefreshToken(value: String): ResponseCookie = make("refresh", value, path = "/auth/token")
    }
}
