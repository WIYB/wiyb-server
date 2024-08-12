package com.wiyb.server.core.domain.product

import com.wiyb.server.core.config.annotation.ValueOfEnum
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class ProductDetailParameterDto(
    @field:NotNull
    @field:Positive
    val productId: Long,
    @field:NotBlank
    @field:ValueOfEnum(enumClass = EquipmentType::class, message = "invalid equipment type")
    val productType: String
)
