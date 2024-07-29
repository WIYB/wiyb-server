package com.wiyb.server.core.handler.auth

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler

class CustomLogoutSuccessHandler : LogoutSuccessHandler {
    override fun onLogoutSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        response?.status = HttpStatus.OK.value()
        response?.contentType = MediaType.APPLICATION_JSON_VALUE
        response?.characterEncoding = "UTF-8"

        response?.writer?.write("{}")
        response?.writer?.flush()
    }
}
