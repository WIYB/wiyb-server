package com.wiyb.server.storage.cache.repository

import com.wiyb.server.storage.cache.CacheContextTest
import com.wiyb.server.storage.cache.entity.CachedProduct
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

class CachedProductRepositoryTest(
    private val cachedProductRepository: CachedProductRepository
) : CacheContextTest() {
    @Test
    @DisplayName("ProductViewCount 조회 및 저장")
    fun findAndSaveTest() {
        val cachedProduct =
            CachedProduct(
                1,
                "type",
                "brand",
                "name",
                0,
                "2021",
                emptyList()
            )

        cachedProductRepository.save(cachedProduct)
        cachedProduct.increase()
        cachedProductRepository.save(cachedProduct)

        val target = cachedProductRepository.findById(cachedProduct.id).orElse(null)

        assertThat(target).isNotNull
        assertThat(target.id).isEqualTo(cachedProduct.id)
        assertThat(target.reviewCount).isEqualTo(1)
    }
}
