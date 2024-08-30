package com.wiyb.server.core.facade

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.core.domain.user.CreateUserProfileDto
import com.wiyb.server.core.domain.user.UpdateUserProfileDto
import com.wiyb.server.core.service.UserProfileService
import com.wiyb.server.core.service.UserService
import com.wiyb.server.storage.database.entity.user.constant.Role
import com.wiyb.server.storage.database.entity.user.dto.UserProfileDto
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

        return UserProfileDto(
            user.id.toString(),
            userProfile.nickname,
            userProfile.gender,
            userProfile.birth,
            userProfile.handy,
            userProfile.height,
            userProfile.weight,
            userProfile.imageUrl,
            userProfile.createdAt
        )
    }

    fun getProfile(userId: String?): UserProfileDto =
        if (userId != null) {
            userService.findUserProfileDtoById(userId)
        } else {
            val sessionId = SecurityContextHolder.getContext().authentication.name
            userService.findUserProfileDtoBySessionId(sessionId)
        }

    fun updateProfile(updateUserProfileDto: UpdateUserProfileDto): UserProfileDto {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val user = userService.findWithUserProfileBySessionId(sessionId)
        val userProfile = updateUserProfileDto.updateEntity(user.userProfile!!)

        userProfileService.save(userProfile)

        return UserProfileDto(
            user.id.toString(),
            userProfile.nickname,
            userProfile.gender,
            userProfile.birth,
            userProfile.handy,
            userProfile.height,
            userProfile.weight,
            userProfile.imageUrl,
            userProfile.createdAt
        )
    }

    fun deleteProfile() {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val user = userService.findBySessionId(sessionId)

        userService.delete(user)
    }
}
