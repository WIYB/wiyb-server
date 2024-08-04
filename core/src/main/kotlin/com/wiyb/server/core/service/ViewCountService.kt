package com.wiyb.server.core.service

import com.wiyb.server.storage.cache.entity.ProductViewCount
import com.wiyb.server.storage.cache.entity.UserVisitLog
import com.wiyb.server.storage.cache.repository.ProductViewCountRepository
import com.wiyb.server.storage.cache.repository.UserVisitLogRepository
import org.springframework.stereotype.Service

@Service
class ViewCountService(
    private val userVisitLogRepository: UserVisitLogRepository,
    private val productViewCountRepository: ProductViewCountRepository
) {
    fun applyUserVisitLog(
        userId: Long,
        productId: Long
    ) {
        val userVisitLog = userVisitLogRepository.findById(userId).orElse(UserVisitLog(userId))
        userVisitLog.append(productId)
        userVisitLogRepository.save(userVisitLog)
    }

    fun increaseProductViewCount(productId: Long) {
        val productViewCount = productViewCountRepository.findById(productId).orElse(ProductViewCount(productId))
        productViewCount.increase()
        productViewCountRepository.save(productViewCount)
    }
}
