package com.wiyb.server.core.domain.product

import com.wiyb.server.core.config.annotation.ValueOfEnum
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.dto.metric.constant.EvaluationType
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class PopularProductByMetricQuery(
    @field:NotBlank
    @field:ValueOfEnum(enumClass = EquipmentType::class)
    val type: String,
    @field:NotBlank
    @field:ValueOfEnum(enumClass = EvaluationType::class)
    val metric: String,
    @field:Min(5)
    val size: Int = 5
)
