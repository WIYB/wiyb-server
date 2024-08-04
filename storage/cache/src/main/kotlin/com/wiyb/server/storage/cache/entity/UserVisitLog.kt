package com.wiyb.server.storage.cache.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("user_visit_log")
data class UserVisitLog(
    @Id
    val id: Long,
    val visited: MutableSet<Long> = mutableSetOf()
) : Serializable {
    fun append(productId: Long) {
        visited.add(productId)
    }
}
