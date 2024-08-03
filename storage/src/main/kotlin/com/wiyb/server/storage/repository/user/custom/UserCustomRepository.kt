package com.wiyb.server.storage.repository.user.custom

import com.wiyb.server.storage.entity.user.User
import com.wiyb.server.storage.entity.user.dto.UserProfileDto
import com.wiyb.server.storage.entity.user.dto.UserSimpleProfileDto

interface UserCustomRepository {
    fun findBySessionId(sessionId: String): User?

    fun findUserProfileDtoById(userId: Long): UserProfileDto?

    fun findUserProfileDtoBySessionId(sessionId: String): UserProfileDto?

    fun findUserSimpleProfileDtoByNameKeyword(keyword: String): List<UserSimpleProfileDto>

    fun findWithUserProfileBySessionId(sessionId: String): User?
}
