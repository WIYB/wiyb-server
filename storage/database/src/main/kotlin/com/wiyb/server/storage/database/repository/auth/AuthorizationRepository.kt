package com.wiyb.server.storage.database.repository.auth

import com.wiyb.server.storage.database.entity.auth.Authorization
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorizationRepository : JpaRepository<Authorization, Long> {
    @EntityGraph(attributePaths = ["user"])
    fun findFirstBySessionIdOrderByCreatedAtDesc(sessionId: String): Authorization?

    fun deleteAllBySessionId(sessionId: String)
}
