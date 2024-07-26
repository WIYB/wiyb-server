package com.wiyb.server.core.domain.auth

import com.wiyb.server.core.domain.common.CustomCookie
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponseWrapper
import org.springframework.http.MediaType

class TokenResponseWrapper(
    response: HttpServletResponse
) : HttpServletResponseWrapper(response) {
    fun send(issueTokenDto: IssueTokenDto) {
        addCookie(CustomCookie.makeForAccessToken(issueTokenDto.accessToken))
        addCookie(CustomCookie.makeForRefreshToken(issueTokenDto.refreshToken))

        status = SC_OK
        contentType = MediaType.APPLICATION_JSON_VALUE
        characterEncoding = "UTF-8"

        writer.write("{}")
        writer.flush()
    }
}
