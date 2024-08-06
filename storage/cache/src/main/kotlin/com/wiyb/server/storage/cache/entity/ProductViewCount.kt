package com.wiyb.server.storage.cache.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("product_view_count")
data class ProductViewCount(
    @Id
    val id: Long,
    var dailyCount: Long = 0,
    var count: Long = 0
) : Serializable {
    fun increase() {
        count++
    }

    fun clearCount() {
        dailyCount += count
        count = 0
    }
}
