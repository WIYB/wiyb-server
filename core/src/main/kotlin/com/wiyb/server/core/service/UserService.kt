package com.wiyb.server.core.service

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.storage.entity.User
import com.wiyb.server.storage.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun findUserBySessionId(sessionId: String): User =
        userRepository.findBySessionId(sessionId) ?: throw CommonException(
            ErrorCode.USER_NOT_FOUND
        )

    fun save(user: User) {
        userRepository.save(user)
    }
}
