package com.wiyb.server.storage.cache.repository

import com.wiyb.server.storage.cache.entity.CachedProduct
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.ListCrudRepository

interface CachedProductRepository :
    CrudRepository<CachedProduct, Long>,
    ListCrudRepository<CachedProduct, Long> {
    fun findTop10ByTypeOrderByWeeklyViewCountDesc(type: String): List<CachedProduct>

    fun findTop10ByOrderByWeeklyViewCountDesc(): List<CachedProduct>

    fun findTop100ByOrderByWeeklyViewCountDesc(): List<CachedProduct>

    fun findTop10ByTypeOrderByDailyViewCountDesc(type: String): List<CachedProduct>

    fun findTop10ByOrderByDailyViewCountDesc(): List<CachedProduct>
}
