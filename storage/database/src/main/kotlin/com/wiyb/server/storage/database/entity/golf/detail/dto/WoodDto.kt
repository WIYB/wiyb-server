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

    val loftDegree: String? = equipment.loftDegree?.joinToString(separator = ", ") { "$it°" }
    val isLoftChangeable: Boolean? = equipment.isLoftChangeable
    val isWeightChangeable: Boolean? = equipment.isWeightChangeable
    val loftSpec: List<WoodLoftSpecDto>? = makeLoftSpec()

    private fun makeLoftSpec(): List<WoodLoftSpecDto>? {
        if (equipment.numbers.isNullOrEmpty() || equipment.loftDegree.isNullOrEmpty() || equipment.lieAngle.isNullOrEmpty()) return null

        return equipment.numbers!!.mapIndexed { index, number ->
            WoodLoftSpecDto(
                number = number,
                loftDegree = equipment.loftDegree!![index],
                lieAngle = equipment.lieAngle!![index]
            )
        }
    }
}
