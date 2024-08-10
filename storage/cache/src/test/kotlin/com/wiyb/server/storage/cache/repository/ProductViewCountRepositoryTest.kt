package com.wiyb.server.storage.cache.repository

import com.wiyb.server.storage.cache.CacheContextTest
import com.wiyb.server.storage.cache.entity.ProductViewCount
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

class ProductViewCountRepositoryTest(
    private val productViewCountRepository: ProductViewCountRepository
) : CacheContextTest() {
    @Test
    @DisplayName("ProductViewCount 조회 및 저장")
    fun findAndSaveTest() {
        val productViewCount = ProductViewCount(123L)

        productViewCountRepository.save(productViewCount)
        productViewCount.increase()
        productViewCountRepository.save(productViewCount)

        val target = productViewCountRepository.findById(productViewCount.id).orElse(null)

        assertThat(target).isNotNull
        assertThat(target.id).isEqualTo(productViewCount.id)
        assertThat(target.count).isEqualTo(1)
    }
}
