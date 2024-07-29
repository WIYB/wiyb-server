package com.wiyb.server.core.facade

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.core.domain.user.CreateUserInfoDto
import com.wiyb.server.core.domain.user.UserInfoDto
import com.wiyb.server.core.service.UserService
import com.wiyb.server.storage.entity.constant.Role
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userService: UserService
) {
    fun createProfile(createUserInfoDto: CreateUserInfoDto): UserInfoDto {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val user = userService.findUserBySessionId(sessionId)

        if (user.nickname != null) {
            throw CommonException(ErrorCode.INSUFFICIENT_AUTHORITY)
        }

        user.update(
            role = Role.USER,
            nickname = createUserInfoDto.nickname,
            gender = createUserInfoDto.gender,
            birth = createUserInfoDto.birth
        )
        userService.save(user)

        return UserInfoDto.fromEntity(user)
    }

    fun deleteProfile() {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val user = userService.findUserBySessionId(sessionId)

        userService.delete(user)
    }
}
