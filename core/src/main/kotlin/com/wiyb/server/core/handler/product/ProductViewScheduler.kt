package com.wiyb.server.core.handler.product

import com.wiyb.server.core.facade.ProductViewFacade
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ProductViewScheduler(
    private val productViewFacade: ProductViewFacade
) {
    // 5분 간격으로 실행
    @Scheduled(cron = "0 */5 * * * *")
    fun atTimeSharp() {
        productViewFacade.increaseAllProductViewCount()
    }

    // 매일 자정에 실행
    @Scheduled(cron = "0 0 0 * * *")
    fun atMidnight() {
        productViewFacade.clearAllProductViewCount()
    }
}
