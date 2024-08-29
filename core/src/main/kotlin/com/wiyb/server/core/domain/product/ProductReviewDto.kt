package com.wiyb.server.core.domain.product

import com.wiyb.server.storage.database.entity.golf.dto.EquipmentReviewDto
import com.wiyb.server.storage.database.entity.user.dto.UserSimpleProfileDto
import java.time.LocalDateTime

data class ProductReviewDto(
    val id: String,
    val isLiked: Boolean,
    val content: String,
    val imageUrls: List<String>?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val user: UserSimpleProfileDto
) {
    companion object {
        fun from(
            dto: EquipmentReviewDto,
            like: Boolean?
        ): ProductReviewDto =
            ProductReviewDto(
                id = dto.id,
                isLiked = like ?: false,
                content = dto.content,
                imageUrls = dto.imageUrls,
                createdAt = dto.createdAt,
                updatedAt = dto.updatedAt,
                user = dto.user
            )

        fun from(
            dtoList: List<EquipmentReviewDto>,
            likeIds: List<Long>? = null
        ): List<ProductReviewDto> = dtoList.mapIndexed { index, it -> from(it, likeIds?.contains(it.id.toLong())) }
    }
}
