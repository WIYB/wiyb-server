package com.wiyb.server.storage.cache.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.io.Serializable

@RedisHash("search_keyword")
data class SearchKeyword(
    @Id
    val keyword: String,
    @Indexed
    var dailyHitCount: Long = 0,
    @Indexed
    var weeklyHitCount: Long = 0,
    private val weeklyChart: MutableList<Long> = MutableList(7) { 0 }
) : Serializable {
    fun increase() {
        dailyHitCount++
        weeklyHitCount++
    }

    fun applyWeeklyCount(): Long {
        val erasedCount = weeklyChart.removeLast()
        weeklyChart.addFirst(dailyHitCount)
        dailyHitCount = 0
        weeklyHitCount -= erasedCount
        return erasedCount
    }
}
