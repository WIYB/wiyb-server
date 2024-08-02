package com.wiyb.server.core.controller

import com.wiyb.server.core.domain.auth.TokenResponseWrapper
import com.wiyb.server.core.facade.AuthFacade
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authFacade: AuthFacade
) {
    @PatchMapping("/token")
    fun refreshToken(response: HttpServletResponse) {
        val tokenDto = authFacade.refreshToken()
        TokenResponseWrapper.send(response, tokenDto)
    }
}
