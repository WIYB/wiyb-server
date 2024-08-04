package com.wiyb.server.core.handler.product

import com.wiyb.server.core.service.ProductService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ProductViewScheduler(
    private val productService: ProductService
) {
    // 매 시간 0분 0초에 실행
    @Scheduled(cron = "0 0 * * * *")
    fun atTimeSharp() {
        TODO("product view count 저장 로직")
    }

    // 매일 자정에 실행
    @Scheduled(cron = "0 0 0 * * *")
    fun atMidnight() {
        TODO("user visit log 저장, 인기 장비 업데이트")
    }
}
