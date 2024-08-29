package com.wiyb.server.core.domain.product

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class ProductReviewLikePathDto(
    @field:NotNull
    @field:Positive
    val productId: Long,
    @field:NotNull
    @field:Positive
    val reviewId: Long
)
