package com.wiyb.server.core.controller

import com.wiyb.server.core.domain.product.PostProductReviewDto
import com.wiyb.server.core.facade.ProductFacade
import com.wiyb.server.core.facade.ProductViewFacade
import com.wiyb.server.storage.cache.entity.MostViewedProduct
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentReviewDto
import jakarta.annotation.security.RolesAllowed
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController(
    private val productFacade: ProductFacade,
    private val productViewFacade: ProductViewFacade
) {
    @GetMapping("/most/view/simple")
    fun getMostViewed(): ResponseEntity<List<MostViewedProduct>> = ResponseEntity.ok().body(productViewFacade.getMostViewedProduct())

    @GetMapping("/{productId}/review")
    fun getProductReviews(
        @PathVariable("productId") productId: Long
    ): ResponseEntity<List<EquipmentReviewDto>> {
        val reviews = productFacade.getProductReviews(productId)
        return ResponseEntity.ok().body(reviews)
    }

    @RolesAllowed("ROLE_USER", "ROLE_ADMIN")
    @PostMapping("/{productId}/review")
    fun postProductReview(
        @PathVariable("productId") productId: Long,
        @RequestBody dto: PostProductReviewDto
    ): ResponseEntity<Unit> {
        productFacade.postProductReview(productId, dto)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{productId}")
    fun getProductDetail(
        @PathVariable("productId") productId: Long
    ): ResponseEntity<EquipmentDto> {
        val productDetailDto = productFacade.getProductDetail(productId)
        return ResponseEntity.ok().body(productDetailDto)
    }

    // todo: delete
    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/popular/setting")
    fun popularSetting() {
        productViewFacade.clearAllProductViewCount()
    }
}
