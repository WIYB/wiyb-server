package com.wiyb.server.storage.repository.user.custom

import com.wiyb.server.storage.entity.user.User

interface UserCustomRepository {
    fun findBySessionId(sessionId: String): User?

    fun findWithUserProfileById(userId: Long): User?

    fun findWithUserProfileBySessionId(sessionId: String): User?
}
