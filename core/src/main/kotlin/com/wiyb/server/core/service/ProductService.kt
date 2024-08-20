package com.wiyb.server.core.service

import com.wiyb.server.storage.cache.entity.CachedProduct
import com.wiyb.server.storage.cache.entity.UserVisitLog
import com.wiyb.server.storage.cache.repository.CachedProductRepository
import com.wiyb.server.storage.cache.repository.UserVisitLogRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val userVisitLogRepository: UserVisitLogRepository,
    private val cachedProductRepository: CachedProductRepository
) {
    fun existsById(productId: Long): Boolean = cachedProductRepository.existsById(productId)

    fun addUserVisitLog(
        userId: Long,
        productId: Long
    ) {
        val userVisitLog = userVisitLogRepository.findById(userId).orElse(UserVisitLog(userId))

        // 조회수 중복 방지 코드 제거
        userVisitLog.append(productId)
        userVisitLogRepository.save(userVisitLog)
    }

    fun increaseViewCount(product: CachedProduct) {
        product.increase()
        cachedProductRepository.save(product)
    }

    fun findById(id: Long): CachedProduct? = cachedProductRepository.findById(id).orElse(null)

    fun findAll(): List<CachedProduct> = cachedProductRepository.findAll()

    fun findWeeklyProductByType(type: String? = null) =
        when (type) {
            null -> cachedProductRepository.findTop10ByOrderByWeeklyViewCountDesc()
            else -> cachedProductRepository.findTop10ByTypeOrderByWeeklyViewCountDesc(type)
        }

    fun findDailyProductByType(type: String? = null) =
        when (type) {
            null -> cachedProductRepository.findTop10ByOrderByDailyViewCountDesc()
            else -> cachedProductRepository.findTop10ByTypeOrderByDailyViewCountDesc(type)
        }

    fun saveAllProductViewCounts(cachedProducts: List<CachedProduct>): List<CachedProduct> = cachedProductRepository.saveAll(cachedProducts)

    fun deleteAllUserViewLog() = userVisitLogRepository.deleteAll()

    fun deleteAllProductViewCount() = cachedProductRepository.deleteAll()
}
