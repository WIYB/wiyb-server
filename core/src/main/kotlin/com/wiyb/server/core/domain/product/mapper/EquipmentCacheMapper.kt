package com.wiyb.server.core.domain.product.mapper

import TimeRange
import com.wiyb.server.storage.cache.entity.CachedProduct
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto

class EquipmentCacheMapper {
    companion object {
        fun fromSimpleDto(dto: EquipmentSimpleDto): CachedProduct =
            CachedProduct(
                dto.id.toLong(),
                dto.brand,
                dto.type.toString(),
                dto.name,
                dto.reviewCount,
                dto.releasedYear,
                dto.imageUrls
            )

        fun fromSimpleDtoList(dtoList: List<EquipmentSimpleDto>): List<CachedProduct> = dtoList.map { fromSimpleDto(it) }

        fun fromCachedDto(
            dto: CachedProduct,
            range: TimeRange
        ): EquipmentSimpleDto =
            EquipmentSimpleDto(
                dto.id.toString(),
                dto.brand,
                EquipmentType.fromCode(dto.type),
                dto.name,
                if (range == TimeRange.WEEKLY) dto.weeklyViewCount else dto.dailyViewCount,
                dto.reviewCount,
                dto.releasedYear,
                dto.imageUrls
            )

        fun fromCachedDtoList(
            dtoList: List<CachedProduct>,
            range: TimeRange
        ): List<EquipmentSimpleDto> = dtoList.map { fromCachedDto(it, range) }
    }
}
