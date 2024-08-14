package com.wiyb.server.core.facade

import TimeRange
import com.wiyb.server.core.domain.product.mapper.EquipmentCacheMapper
import com.wiyb.server.core.service.EquipmentService
import com.wiyb.server.core.service.ProductService
import com.wiyb.server.storage.cache.entity.CachedProduct
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import org.springframework.stereotype.Component

@Component
class ProductViewFacade(
    private val productService: ProductService,
    private val equipmentService: EquipmentService
) {
    fun getMostViewedProductSimple(
        type: EquipmentType?,
        range: TimeRange
    ): List<CachedProduct> {
        when (range) {
            TimeRange.DAILY, TimeRange.WEEKLY -> {
                val products =
                    if (range == TimeRange.WEEKLY) {
                        productService.findWeeklyProductByType(type?.toString())
                    } else {
                        productService.findDailyProductByType(type?.toString())
                    }

                if (products.isNotEmpty()) {
                    val counts = equipmentService.findReviewCounts(products.map { it.id })
                    products.forEachIndexed { index, product ->
                        product.reviewCount = counts[index]
                    }
                }

                return products
            }

            else -> {
                val products = equipmentService.findMostViewedProduct(type)
                return EquipmentCacheMapper.fromSimpleDtoList(products)
            }
        }
    }

    //    @Transactional
    fun increaseAllProductViewCount() {
//        val products = productService.findAll()
//
//        products.forEach { it.clearCount() }
//        productService.saveAllProductViewCounts(products)
    }

//    @Transactional
    fun clearAllProductViewCount() {
//        val products = productService.findAll()
//        productService.deleteAllProductViewCount()
//        products.sortedBy { it.dailyCount }.reversed()
//
//        val topProducts = products.take(5)
//        val productIdList = topProducts.map { it.id }
//        val equipments = equipmentService.findById(productIdList)
//
//        val mostViewedProducts =
//            topProducts.mapIndexed { index, product ->
//                MostViewedProduct(
//                    product.id.toString(),
//                    equipments[index].brand,
//                    equipments[index].type.getCode(),
//                    equipments[index].name,
//                    equipments[index].reviewCount,
//                    product.dailyCount,
//                    equipments[index].releasedYear,
//                    equipments[index].imageUrls
//                )
//            }
//
//        productService.deleteAllMostViewedProduct()
//        productService.saveAllMostViewedProducts(mostViewedProducts)
//
//        // todo: user visit log를 db에도 저장
//        productService.deleteAllUserViewLog()
    }
}
