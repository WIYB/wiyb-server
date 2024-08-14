package com.wiyb.server.core.domain.product

import TimeRange
import com.wiyb.server.core.config.annotation.ValueOfEnum
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType

data class ProductTypeQueryDto(
    @field:ValueOfEnum(enumClass = EquipmentType::class)
    val type: String? = null,
    @field:ValueOfEnum(enumClass = TimeRange::class)
    val range: String? = null
)
