package com.wiyb.server.core.controller

import TimeRange
import com.wiyb.server.core.domain.product.PostProductReviewDto
import com.wiyb.server.core.domain.product.ProductDetailDto
import com.wiyb.server.core.domain.product.ProductDetailParameterDto
import com.wiyb.server.core.domain.product.ProductIdDto
import com.wiyb.server.core.domain.product.ProductReviewDto
import com.wiyb.server.core.domain.product.ProductReviewLikePathDto
import com.wiyb.server.core.domain.product.ProductTypeQueryDto
import com.wiyb.server.core.facade.ProductFacade
import com.wiyb.server.core.facade.ProductViewFacade
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.DeleteMapping
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
    fun getMostViewed(
        @Valid query: ProductTypeQueryDto
    ): ResponseEntity<List<EquipmentSimpleDto>> =
        ResponseEntity.ok().body(
            productViewFacade.getMostViewedProductSimple(
                type = query.type?.let { EquipmentType.fromCode(it) },
                range = query.range?.let { TimeRange.fromCode(it) } ?: TimeRange.WEEKLY
            )
        )

    @GetMapping("/{productId}/{productType}")
    fun getProductDetail(
        @Valid parameter: ProductDetailParameterDto
    ): ResponseEntity<ProductDetailDto> {
        val productDetailDto =
            productFacade.getProductDetail(
                parameter.productId,
                enumValueOf<EquipmentType>(parameter.productType.uppercase())
            )
        return ResponseEntity.ok().body(productDetailDto)
    }

    @GetMapping("/{productId}/review")
    fun getProductReviews(
        @Valid path: ProductIdDto
    ): ResponseEntity<List<ProductReviewDto>> {
        val reviews = productFacade.getProductReviews(path.productId)
        return ResponseEntity.ok().body(reviews)
    }

    @Secured("ROLE_USER")
    @PostMapping("/{productId}/review")
    fun postProductReview(
        @Valid path: ProductIdDto,
        @RequestBody @Valid dto: PostProductReviewDto
    ): ResponseEntity<Unit> {
        productFacade.postProductReview(path.productId, dto)
        return ResponseEntity.ok().build()
    }

    @Secured("ROLE_USER")
    @PostMapping("/{productId}/review/{reviewId}/like")
    fun likeProductReview(
        @Valid path: ProductReviewLikePathDto
    ): ResponseEntity<ProductReviewLikePathDto> {
        productFacade.likeProductReview(path)
        return ResponseEntity.ok().body(path)
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/{productId}/review/{reviewId}/like")
    fun unLikeProductReview(
        @Valid path: ProductReviewLikePathDto
    ): ResponseEntity<Unit> {
        productFacade.unlikeProductReview(path)
        return ResponseEntity.ok().build()
    }

    @Secured("ROLE_USER")
    @PostMapping("/{productId}/bookmark")
    fun bookmarkProduct(
        @Valid path: ProductIdDto
    ): ResponseEntity<Unit> {
        productFacade.bookmarkProduct(path.productId)
        return ResponseEntity.ok().build()
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/{productId}/bookmark")
    fun unBookmarkProduct(
        @Valid path: ProductIdDto
    ): ResponseEntity<Unit> {
        productFacade.unBookmarkProduct(path.productId)
        return ResponseEntity.ok().build()
    }

    // todo: delete
    @Secured("ROLE_ADMIN")
    @GetMapping("/popular/setting")
    fun popularSetting() {
        productViewFacade.clearAllProductViewCount()
    }
}
