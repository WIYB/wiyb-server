package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.wiyb.server.storage.database.entity.golf.detail.Hybrid
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractHybrid

data class HybridDto(
    private val equipment: Hybrid
) : AbstractHybrid {
    data class HybridLoftSpecDto(
        val number: String?,
        val loftDegree: String?,
        val lieAngle: String?
    )

    val isLoftChangeable: Boolean? = equipment.isLoftChangeable
    val isWeightChangeable: Boolean? = equipment.isWeightChangeable
    val loftSpec: List<HybridLoftSpecDto>? =
        equipment.loftDegree?.mapIndexed { index, s ->
            HybridLoftSpecDto(
                number = equipment.numbers?.getOrNull(index),
                loftDegree = s,
                lieAngle = equipment.lieAngle?.getOrNull(index)
            )
        }
}
