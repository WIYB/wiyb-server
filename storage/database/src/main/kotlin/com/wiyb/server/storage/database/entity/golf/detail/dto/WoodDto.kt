package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.wiyb.server.storage.database.entity.golf.detail.Wood
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractWood

data class WoodDto(
    private val equipment: Wood
) : AbstractWood {
    data class WoodLoftDetailDto(
        val number: String?,
        val loftDegree: String?,
        val lieAngle: String?
    )

    val loftDegree: String? = equipment.loftDegree?.joinToString(",")
    val isLoftChangeable: Boolean? = equipment.isLoftChangeable
    val isWeightChangeable: Boolean? = equipment.isWeightChangeable
    val loftSpecDetail: List<WoodLoftDetailDto>? =
        equipment.loftDegree?.mapIndexed { index, s ->
            WoodLoftDetailDto(
                number = equipment.numbers?.getOrNull(index),
                loftDegree = s,
                lieAngle = equipment.lieAngle?.getOrNull(index)
            )
        }
}
