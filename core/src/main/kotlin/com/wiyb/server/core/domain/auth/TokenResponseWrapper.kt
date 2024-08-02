package com.wiyb.server.core.domain.auth

import com.wiyb.server.core.domain.common.CustomCookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

class TokenResponseWrapper {
    companion object {
        fun addCookie(
            response: HttpServletResponse,
            issueTokenDto: IssueTokenDto
        ) {
            response.addCookie(CustomCookie.makeForAccessToken(issueTokenDto.accessToken))
            response.addCookie(CustomCookie.makeForRefreshToken(issueTokenDto.refreshToken))
        }

        fun send(
            response: HttpServletResponse,
            issueTokenDto: IssueTokenDto
        ) {
            addCookie(response, issueTokenDto)

            response.status = HttpStatus.OK.value()
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.characterEncoding = "UTF-8"

            response.writer.write("{}")
            response.writer.flush()
        }

        fun <T : Any> send(
            issueTokenDto: IssueTokenDto,
            body: T
        ): ResponseEntity<T> =
            ResponseEntity
                .ok()
                .header("Set-Cookie", CustomCookie.makeForAccessToken(issueTokenDto.accessToken).toString())
                .header("Set-Cookie", CustomCookie.makeForRefreshToken(issueTokenDto.refreshToken).toString())
                .body(body)
    }
}
