package com.wiyb.server.core.domain.common

import jakarta.servlet.http.Cookie

class CustomCookie(
    name: String,
    value: String
) : Cookie(name, value) {
    companion object {
        fun make(
            name: String,
            value: String,
            path: String = "/",
            httpOnly: Boolean = true,
            sameSite: String = "Lax",
            secure: Boolean = true
        ): Cookie {
            val cookie = Cookie(name, value)
            cookie.path = path
            cookie.isHttpOnly = httpOnly
            cookie.secure = secure
            cookie.setAttribute("sameSite", sameSite)
            return cookie
        }

        fun makeForAccessToken(accessToken: String): Cookie = make("access", accessToken, httpOnly = false)

        fun makeForRefreshToken(refreshToken: String): Cookie = make("refresh", refreshToken, path = "/auth/token", httpOnly = false)
    }
}
