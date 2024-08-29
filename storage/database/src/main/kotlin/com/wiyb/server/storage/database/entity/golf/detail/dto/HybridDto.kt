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

    val loftDegree: String? = equipment.loftDegree?.joinToString(separator = ", ") { "$it°" }
    val isLoftChangeable: Boolean? = equipment.isLoftChangeable
    val isWeightChangeable: Boolean? = equipment.isWeightChangeable
    val loftSpec: List<HybridLoftSpecDto>? = makeLoftSpec()

    private fun makeLoftSpec(): List<HybridLoftSpecDto>? {
        if (equipment.numbers.isNullOrEmpty() || equipment.loftDegree.isNullOrEmpty() || equipment.lieAngle.isNullOrEmpty()) return null

        return equipment.numbers!!.mapIndexed { index, number ->
            HybridLoftSpecDto(
                number = number,
                loftDegree = equipment.loftDegree!![index],
                lieAngle = equipment.lieAngle!![index]
            )
        }
    }
}
