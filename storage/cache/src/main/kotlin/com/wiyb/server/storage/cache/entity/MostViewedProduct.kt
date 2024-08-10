package com.wiyb.server.storage.cache.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("most_viewed_product")
data class MostViewedProduct(
    @Id
    val id: String,
    val brand: String,
    val type: String,
    val name: String,
    val reviewCount: Long,
    val releasedYear: String?,
    val imageUrls: List<String>?,
    val viewCount: Long
)
