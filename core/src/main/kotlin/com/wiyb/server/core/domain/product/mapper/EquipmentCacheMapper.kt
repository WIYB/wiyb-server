package com.wiyb.server.core.domain.product.mapper

import com.wiyb.server.storage.cache.entity.CachedProduct
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
    }
}
