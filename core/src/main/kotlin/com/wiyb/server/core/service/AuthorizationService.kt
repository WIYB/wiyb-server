package com.wiyb.server.core.service

import com.wiyb.server.storage.entity.auth.Authorization
import com.wiyb.server.storage.entity.user.User
import com.wiyb.server.storage.repository.auth.AuthorizationRepository
import org.springframework.stereotype.Service

@Service
class AuthorizationService(
    private val authorizationRepository: AuthorizationRepository
) {
    fun findLatestBySessionIdWithUser(sessionId: String): Authorization? =
        authorizationRepository.findFirstBySessionIdOrderByCreatedAtDesc(sessionId)

    fun save(
        user: User,
        sessionId: String
    ): Authorization {
        val authorization =
            Authorization(
                sessionId = sessionId,
                user = user
            )
        authorizationRepository.save(authorization)

        return authorization
    }

    fun deleteAllBySessionId(sessionId: String) {
        authorizationRepository.deleteAllBySessionId(sessionId)
    }
}
