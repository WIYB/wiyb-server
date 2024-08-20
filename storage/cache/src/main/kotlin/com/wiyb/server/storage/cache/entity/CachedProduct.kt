package com.wiyb.server.storage.cache.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.io.Serializable

@RedisHash("cached_product")
data class CachedProduct(
    @Id
    val id: Long,
    @Indexed
    val brand: String,
    @Indexed
    val type: String,
    @Indexed
    val name: String,
    var reviewCount: Long = 0,
    val releasedYear: String?,
    val imageUrls: List<String>?,
    @Indexed
    var dailyViewCount: Long = 0,
    @Indexed
    var weeklyViewCount: Long = 0,
    private val weeklyChart: MutableList<Long> = MutableList(7) { 0 }
) : Serializable {
    fun increase() {
        dailyViewCount++
        weeklyViewCount++
    }

    fun applyWeeklyCount(): Long {
        val erasedCount = weeklyChart.removeLast()
        weeklyChart.addFirst(dailyViewCount)
        dailyViewCount = 0
        weeklyViewCount -= erasedCount
        return erasedCount
    }
}
