package com.wiyb.server.core.domain.common

import jakarta.servlet.http.Cookie

class CustomCookie {
    companion object {
        fun make(
            name: String,
            value: String,
            path: String = "/",
            httpOnly: Boolean = true,
            secure: Boolean = true,
            sameSite: String = "None"
        ): Cookie {
            val cookie = Cookie(name, value)
            cookie.path = path
            cookie.isHttpOnly = httpOnly
            cookie.secure = secure
            cookie.setAttribute("SameSite", sameSite)
            return cookie
        }

        fun makeForAccessToken(token: String): Cookie = make("access", token)

        fun makeForRefreshToken(token: String): Cookie = make("refresh", token, path = "/auth/token")
    }
}
