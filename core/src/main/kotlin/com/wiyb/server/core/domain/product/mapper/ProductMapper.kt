package com.wiyb.server.core.domain.product.mapper

import com.wiyb.server.core.domain.product.ProductDetailDto
import com.wiyb.server.core.domain.search.YoutubeSearchResultDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto

class ProductMapper {
    companion object {
        fun to(
            from: EquipmentDto,
            youtubeResults: List<YoutubeSearchResultDto>
        ): ProductDetailDto =
            ProductDetailDto(
                id = from.id,
                brand = from.brand,
                type = from.type,
                name = from.name,
                reviewCount = from.reviewCount,
                viewCount = from.viewCount,
                detail = from.detail,
                releasedYear = from.releasedYear,
                imageUrls = from.imageUrls,
                evaluatedCount = from.evaluatedCount,
                evaluationMetricAverage = from.evaluationMetricAverage,
                youtubeResults = youtubeResults,
                reviews = from.reviews
            )
    }
}
