package com.wiyb.server.storage.repository.user.custom

import com.wiyb.server.storage.entity.user.User
import com.wiyb.server.storage.entity.user.dto.UserProfileDto

interface UserCustomRepository {
    fun findBySessionId(sessionId: String): User?

    fun findUserProfileDtoById(userId: Long): UserProfileDto?

    fun findUserProfileDtoBySessionId(sessionId: String): UserProfileDto?

    fun findWithUserProfileBySessionId(sessionId: String): User?
}
