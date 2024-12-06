package com.wiyb.server.storage.database.repository.user.custom

import com.wiyb.server.storage.database.entity.user.UserProfile

interface UserProfileCustomRepository {
    fun findBySessionId(sessionId: String): UserProfile?
}
