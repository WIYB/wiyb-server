package com.wiyb.server.core.domain.product.mapper

import TimeRange
import com.wiyb.server.storage.cache.entity.CachedProduct
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto

class EquipmentCacheMapper {
    companion object {
        fun to(dto: EquipmentDto): CachedProduct =
            CachedProduct(
                dto.id.toLong(),
                dto.brand,
                dto.type.toString(),
                dto.name,
                dto.reviewCount,
                dto.releasedYear,
                dto.imageUrls
            )

        fun to(dto: EquipmentSimpleDto): CachedProduct =
            CachedProduct(
                dto.id.toLong(),
                dto.brand,
                dto.type.toString(),
                dto.name,
                dto.reviewCount,
                dto.releasedYear,
                dto.imageUrls
            )

        fun to(
            dto: CachedProduct,
            range: TimeRange
        ): EquipmentSimpleDto =
            EquipmentSimpleDto(
                id = dto.id.toString(),
                brand = dto.brand,
                type = EquipmentType.fromCode(dto.type),
                name = dto.name,
                viewCount = if (range == TimeRange.WEEKLY) dto.weeklyViewCount else dto.dailyViewCount,
                reviewCount = dto.reviewCount,
                releasedYear = dto.releasedYear,
                imageUrls = dto.imageUrls
            )

        fun toList(dtoList: List<EquipmentSimpleDto>): List<CachedProduct> = dtoList.map { to(it) }

        fun toList(
            dtoList: List<CachedProduct>,
            range: TimeRange
        ): List<EquipmentSimpleDto> = dtoList.map { to(it, range) }
    }
}
