package com.wiyb.server.storage.repository

import com.wiyb.server.storage.entity.Authorization
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorizationRepository : JpaRepository<Authorization, Long> {
    fun findFirstByUserIdOrderByCreatedAtDesc(userId: Long): Authorization?
}
