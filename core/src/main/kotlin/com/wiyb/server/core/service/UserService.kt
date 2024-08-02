package com.wiyb.server.core.service

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.storage.entity.user.User
import com.wiyb.server.storage.entity.user.dto.UserProfileDto
import com.wiyb.server.storage.repository.user.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun findBySessionId(sessionId: String): User =
        userRepository.findBySessionId(sessionId) ?: throw CommonException(
            ErrorCode.USER_NOT_FOUND
        )

    fun findUserProfileDtoById(userId: String): UserProfileDto =
        userRepository.findUserProfileDtoById(userId.toLong()) ?: throw CommonException(
            ErrorCode.USER_NOT_FOUND
        )

    fun findUserProfileDtoBySessionId(sessionId: String): UserProfileDto =
        userRepository.findUserProfileDtoBySessionId(sessionId) ?: throw CommonException(
            ErrorCode.USER_NOT_FOUND
        )

    fun findWithUserProfileBySessionId(sessionId: String): User =
        userRepository.findWithUserProfileBySessionId(sessionId) ?: throw CommonException(
            ErrorCode.USER_NOT_FOUND
        )

    fun save(user: User) {
        userRepository.save(user)
    }

    fun delete(user: User) {
        userRepository.delete(user)
    }
}
