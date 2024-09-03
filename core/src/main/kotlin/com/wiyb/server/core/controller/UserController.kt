package com.wiyb.server.core.controller

import com.wiyb.server.core.domain.auth.TokenResponseWrapper
import com.wiyb.server.core.domain.user.CreateUserProfileDto
import com.wiyb.server.core.domain.user.UpdateUserProfileDto
import com.wiyb.server.core.domain.user.UserIdDto
import com.wiyb.server.core.facade.AuthFacade
import com.wiyb.server.core.facade.UserFacade
import com.wiyb.server.storage.database.entity.user.dto.UserProfileDto
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
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
    @Secured("ROLE_GUEST")
    @PostMapping
    fun createUser(
        @Valid @RequestBody createUserProfileDto: CreateUserProfileDto
    ): ResponseEntity<UserProfileDto> {
        val userProfileDto = userFacade.createProfile(createUserProfileDto)
        val tokenDto = authFacade.refreshToken()

        return TokenResponseWrapper.send(tokenDto, userProfileDto)
    }

    @GetMapping("/profile/{userId}", "/profile")
    fun getUserProfile(
        @Valid path: UserIdDto
    ): ResponseEntity<UserProfileDto> {
        val userProfileDto = userFacade.getProfile(path.userId)
        return ResponseEntity.ok().body(userProfileDto)
    }

    @GetMapping("/similar/handy/simple")
    fun getSimilarHandySimpleUsers(): ResponseEntity<List<UserProfileDto>> {
        TODO("나와 비슷한 핸디의 플레이어")
    }

    @GetMapping("/similar/body/simple")
    fun getSimilarBodySimpleUsers(): ResponseEntity<List<UserProfileDto>> {
        TODO("나와 비슷한 핸디의 플레이어")
    }

    @PutMapping("/profile")
    fun updateUserProfile(
        @Valid @RequestBody updateUserProfileDto: UpdateUserProfileDto
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
