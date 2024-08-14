package com.wiyb.server.core.controller

import com.wiyb.server.core.domain.product.PostProductReviewDto
import com.wiyb.server.core.domain.product.ProductDetailParameterDto
import com.wiyb.server.core.domain.product.ProductIdDto
import com.wiyb.server.core.facade.ProductFacade
import com.wiyb.server.core.facade.ProductViewFacade
import com.wiyb.server.storage.cache.entity.MostViewedProduct
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentReviewDto
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.GetMapping
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
        @Valid path: ProductIdDto
    ): ResponseEntity<List<EquipmentReviewDto>> {
        val reviews = productFacade.getProductReviews(path.productId)
        return ResponseEntity.ok().body(reviews)
    }

    @Secured("ROLE_USER", "ROLE_ADMIN")
    @PostMapping("/{productId}/review")
    fun postProductReview(
        @Valid path: ProductIdDto,
        @RequestBody dto: PostProductReviewDto
    ): ResponseEntity<Unit> {
        productFacade.postProductReview(path.productId, dto)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{productId}/{productType}")
    fun getProductDetail(
        @Valid parameter: ProductDetailParameterDto
    ): ResponseEntity<EquipmentDto> {
        val productDetailDto =
            productFacade.getProductDetail(
                parameter.productId,
                enumValueOf<EquipmentType>(parameter.productType.uppercase())
            )
        return ResponseEntity.ok().body(productDetailDto)
    }

    // todo: delete
    @Secured("ROLE_ADMIN")
    @GetMapping("/popular/setting")
    fun popularSetting() {
        productViewFacade.clearAllProductViewCount()
    }
}
