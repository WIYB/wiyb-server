package com.wiyb.server.core.controller

import com.wiyb.server.core.domain.auth.TokenResponseWrapper
import com.wiyb.server.core.domain.user.CreateUserProfileDto
import com.wiyb.server.core.domain.user.UpdateUserProfileDto
import com.wiyb.server.core.domain.user.UserProfileDto
import com.wiyb.server.core.facade.AuthFacade
import com.wiyb.server.core.facade.UserFacade
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
        @RequestBody createUserProfileDto: CreateUserProfileDto
    ): ResponseEntity<UserProfileDto> {
        val userProfileDto = userFacade.createProfile(createUserProfileDto)
        val tokenDto = authFacade.refreshToken()

        return TokenResponseWrapper.send(tokenDto, userProfileDto)
    }

    @GetMapping("/profile/{userId}", "/profile")
    fun getUserProfile(
        @PathVariable("userId") userId: String? = null
    ): ResponseEntity<UserProfileDto> {
        val userProfileDto = userFacade.getProfile(userId)
        return ResponseEntity.ok().body(userProfileDto)
    }

    @PutMapping("/profile")
    fun updateUserProfile(
        @RequestBody updateUserProfileDto: UpdateUserProfileDto
    ): ResponseEntity<UserProfileDto> {
        val userProfileDto = userFacade.updateProfile(updateUserProfileDto)
        return ResponseEntity.ok().body(userProfileDto)
    }

    @DeleteMapping
    fun deleteUser(): ResponseEntity<String> {
        userFacade.deleteProfile()
        return ResponseEntity.ok().body("{}")
    }
}
