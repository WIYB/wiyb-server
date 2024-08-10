package com.wiyb.server.core.facade

import com.wiyb.server.core.service.EquipmentService
import com.wiyb.server.core.service.ProductService
import com.wiyb.server.storage.cache.entity.MostViewedProduct
import org.springframework.stereotype.Component

@Component
class ProductViewFacade(
    private val productService: ProductService,
    private val equipmentService: EquipmentService
) {
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
                    equipments[index].reviewCount,
                    equipments[index].releasedYear,
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
