package com.wiyb.server.core.controller

import com.wiyb.server.core.domain.auth.TokenResponseWrapper
import com.wiyb.server.core.domain.user.CreateProfileDto
import com.wiyb.server.core.facade.AuthFacade
import com.wiyb.server.core.facade.UserFacade
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userFacade: UserFacade,
    private val authFacade: AuthFacade
) {
    @PostMapping
    fun createUser(
        @RequestBody createProfileDto: CreateProfileDto,
        response: HttpServletResponse
    ) {
        userFacade.createProfile(createProfileDto)

        val tokenDto = authFacade.refreshToken()
        TokenResponseWrapper(response).send(tokenDto)
    }
}
