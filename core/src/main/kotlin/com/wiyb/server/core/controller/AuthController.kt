package com.wiyb.server.core.controller

import com.wiyb.server.core.domain.auth.CheckTokenDto
import com.wiyb.server.core.domain.auth.TokenResponseWrapper
import com.wiyb.server.core.facade.AuthFacade
import com.wiyb.server.storage.database.entity.user.constant.Role
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authFacade: AuthFacade
) {
    @Secured("ROLE_GUEST")
    @GetMapping("/token")
    fun checkToken(response: HttpServletResponse): ResponseEntity<CheckTokenDto> {
        val role =
            SecurityContextHolder
                .getContext()
                .authentication.authorities
                .first()
                .authority
                .replace("ROLE_", "")
        return ResponseEntity.ok().body(CheckTokenDto(Role.fromCode(role)))
    }

    @PatchMapping("/token")
    fun refreshToken(response: HttpServletResponse) {
        val tokenDto = authFacade.refreshToken()
        TokenResponseWrapper.send(response, tokenDto)
    }
}
