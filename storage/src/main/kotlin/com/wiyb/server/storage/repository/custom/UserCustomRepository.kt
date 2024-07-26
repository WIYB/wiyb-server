package com.wiyb.server.storage.repository.custom

import com.wiyb.server.storage.entity.User

interface UserCustomRepository {
    fun findBySessionId(sessionId: String): User?
}
