package com.wiyb.server.core.facade

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.core.domain.user.CreateUserProfileDto
import com.wiyb.server.core.domain.user.UpdateUserProfileDto
import com.wiyb.server.core.domain.user.UserProfileDto
import com.wiyb.server.core.service.UserProfileService
import com.wiyb.server.core.service.UserService
import com.wiyb.server.storage.entity.user.constant.Role
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userService: UserService,
    private val userProfileService: UserProfileService
) {
    fun createProfile(createUserProfileDto: CreateUserProfileDto): UserProfileDto {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val user = userService.findWithUserProfileBySessionId(sessionId)

        if (user.userProfile != null) {
            throw CommonException(ErrorCode.INSUFFICIENT_AUTHORITY)
        }

        user.updateRole(Role.USER)
        userService.save(user)

        val userProfile = createUserProfileDto.toEntity(user)
        userProfileService.save(userProfile)

        return UserProfileDto.fromEntity(user, userProfile)
    }

    fun getProfile(userId: String?): UserProfileDto {
        val user =
            if (userId != null) {
                userService.findWithUserProfileById(userId)
            } else {
                val sessionId = SecurityContextHolder.getContext().authentication.name
                userService.findWithUserProfileBySessionId(sessionId)
            }

        return UserProfileDto.fromEntity(user, user.userProfile!!)
    }

    fun updateProfile(updateUserProfileDto: UpdateUserProfileDto): UserProfileDto {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val user = userService.findWithUserProfileBySessionId(sessionId)
        val userProfile = updateUserProfileDto.updateEntity(user.userProfile!!)

        userProfileService.save(userProfile)

        return UserProfileDto.fromEntity(user, userProfile)
    }

    fun deleteProfile() {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val user = userService.findBySessionId(sessionId)

        userService.delete(user)
    }
}
