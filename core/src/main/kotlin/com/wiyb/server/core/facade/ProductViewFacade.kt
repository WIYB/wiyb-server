package com.wiyb.server.core.facade

import com.wiyb.server.core.service.EquipmentService
import com.wiyb.server.core.service.ProductService
import com.wiyb.server.storage.cache.entity.MostViewedProduct
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import kotlin.random.Random

// todo: delete
@DependsOn("embeddedRedisConfig")
@Component
class ProductViewFacade(
    private val productService: ProductService,
    private val equipmentService: EquipmentService
) {
    @Profile("local", "local-dev")
    @PostConstruct
    fun init() {
        val equipments = equipmentService.findByNameKeyword("123").take(5)
        val mostViewedProducts =
            equipments
                .mapIndexed { index, product ->
                    MostViewedProduct(
                        product.id,
                        equipments[index].brand,
                        equipments[index].type.getCode(),
                        equipments[index].name,
                        equipments[index].releasedYear,
                        equipments[index].reviewCount,
                        equipments[index].imageUrls,
                        (100 + Random.nextLong(900))
                    )
                }.sortedBy { it.viewCount }
                .reversed()

        productService.saveAllMostViewedProducts(mostViewedProducts)
    }

    fun getPopularProductNames(): List<String> {
        val products = productService.findAllMostViewedProduct()
        return products.map { it.name }
    }

    fun getMostViewedProduct(): List<MostViewedProduct> = productService.findAllMostViewedProduct()

    //    @Transactional
    fun increaseAllProductViewCount() {
        val products = productService.findAll()

        products.forEach { it.clearCount() }
        productService.saveAllProductViewCounts(products)
    }

//    @Transactional
    fun clearAllProductViewCount() {
        val products = productService.findAll()
        productService.deleteAllProductViewCount()
        products.sortedBy { it.dailyCount }.reversed()

        val topProducts = products.take(5)
        val productIdList = topProducts.map { it.id }
        val equipments = equipmentService.findById(productIdList)

        val mostViewedProducts =
            topProducts.mapIndexed { index, product ->
                MostViewedProduct(
                    product.id.toString(),
                    equipments[index].brand,
                    equipments[index].type.getCode(),
                    equipments[index].name,
                    equipments[index].releasedYear,
                    equipments[index].reviewCount,
                    equipments[index].imageUrls,
                    product.dailyCount
                )
            }

        productService.deleteAllMostViewedProduct()
        productService.saveAllMostViewedProducts(mostViewedProducts)

        // todo: user visit log를 db에도 저장
        productService.deleteAllUserViewLog()
    }
}
