package com.wiyb.server.storage.cache.repository

import com.wiyb.server.storage.cache.entity.UserVisitLog
import org.springframework.data.repository.CrudRepository

interface UserVisitLogRepository : CrudRepository<UserVisitLog, Long> {
    fun isVisitedProduct(
        userId: Long,
        productId: Long
    ): Boolean {
        val userVisitLog = findById(userId).orElse(null) ?: return false
        return userVisitLog.visited.contains(productId)
    }

    fun addVisitedLog(
        userId: Long,
        productId: Long
    ) {
        val userVisitLog = findById(userId).orElse(UserVisitLog(userId, mutableSetOf()))
        userVisitLog.visited.add(productId)
        save(userVisitLog)
    }
}
