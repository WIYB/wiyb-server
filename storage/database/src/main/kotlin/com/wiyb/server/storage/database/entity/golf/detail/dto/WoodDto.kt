package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.wiyb.server.storage.database.entity.golf.detail.Wood
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractWood

data class WoodDto(
    private val equipment: Wood
) : AbstractWood {
    data class WoodLoftSpecDto(
        val number: String?,
        val loftDegree: String?,
        val lieAngle: String?
    )

    val isLoftChangeable: Boolean? = equipment.isLoftChangeable
    val isWeightChangeable: Boolean? = equipment.isWeightChangeable
    val loftSpec: List<WoodLoftSpecDto>? =
        equipment.loftDegree?.mapIndexed { index, s ->
            WoodLoftSpecDto(
                number = equipment.numbers?.getOrNull(index),
                loftDegree = s,
                lieAngle = equipment.lieAngle?.getOrNull(index)
            )
        }
}
