package com.wiyb.server.core.service

import com.wiyb.server.storage.cache.entity.MostViewedProduct
import com.wiyb.server.storage.cache.entity.ProductViewCount
import com.wiyb.server.storage.cache.entity.UserVisitLog
import com.wiyb.server.storage.cache.repository.MostViewedProductRepository
import com.wiyb.server.storage.cache.repository.ProductViewCountRepository
import com.wiyb.server.storage.cache.repository.UserVisitLogRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val userVisitLogRepository: UserVisitLogRepository,
    private val productViewCountRepository: ProductViewCountRepository,
    private val mostViewedProductRepository: MostViewedProductRepository
) {
    fun addUserVisitLog(
        userId: Long,
        productId: Long
    ) {
        val userVisitLog = userVisitLogRepository.findById(userId).orElse(UserVisitLog(userId))

        // 조회수 중복 방지 코드 제거
        userVisitLog.append(productId)
        userVisitLogRepository.save(userVisitLog)
    }

    fun increaseProductViewCount(productId: Long) {
        val productViewCount = productViewCountRepository.findById(productId).orElse(ProductViewCount(productId))

        productViewCount.increase()
        productViewCountRepository.save(productViewCount)
    }

    fun findAll(): List<ProductViewCount> = productViewCountRepository.findAll()

    fun findAllMostViewedProduct(): List<MostViewedProduct> = mostViewedProductRepository.findAll()

    fun saveAllProductViewCounts(productViewCounts: List<ProductViewCount>): List<ProductViewCount> =
        productViewCountRepository.saveAll(productViewCounts)

    fun saveAllMostViewedProducts(mostViewedProducts: List<MostViewedProduct>): List<MostViewedProduct> =
        mostViewedProductRepository.saveAll(mostViewedProducts)

    fun deleteAllUserViewLog() = userVisitLogRepository.deleteAll()

    fun deleteAllProductViewCount() = productViewCountRepository.deleteAll()

    fun deleteAllMostViewedProduct() = mostViewedProductRepository.deleteAll()
}
