package com.wiyb.server.storage.cache.repository

import com.wiyb.server.storage.cache.config.EmbeddedRedisConfig
import com.wiyb.server.storage.cache.config.RedisRepositoryConfig
import com.wiyb.server.storage.cache.entity.ProductViewCount
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.Test

@ExtendWith(SpringExtension::class)
@SpringBootTest(
    classes = [EmbeddedRedisConfig::class, RedisRepositoryConfig::class],
    properties = ["spring.config.location = classpath:storage-cache-test.yml"]
)
class ProductViewCountRepositoryTest {
    @Autowired
    private lateinit var productViewCountRepository: ProductViewCountRepository

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
