package com.wiyb.server.core.controller

import com.wiyb.server.core.domain.common.CustomResponseCookie
import com.wiyb.server.core.domain.user.CreateUserInfoDto
import com.wiyb.server.core.domain.user.UserInfoDto
import com.wiyb.server.core.facade.AuthFacade
import com.wiyb.server.core.facade.UserFacade
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
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
        @RequestBody createUserInfoDto: CreateUserInfoDto
    ): ResponseEntity<UserInfoDto> {
        val userInfoDto = userFacade.createProfile(createUserInfoDto)
        val tokenDto = authFacade.refreshToken()

        return ResponseEntity
            .ok()
            .header(HttpHeaders.SET_COOKIE, CustomResponseCookie.makeForAccessToken(tokenDto.accessToken).toString())
            .header(HttpHeaders.SET_COOKIE, CustomResponseCookie.makeForRefreshToken(tokenDto.refreshToken).toString())
            .body(userInfoDto)
    }

    @DeleteMapping
    fun deleteUser(): ResponseEntity<String> {
        userFacade.deleteProfile()
        return ResponseEntity.ok().body("{}")
    }
}
