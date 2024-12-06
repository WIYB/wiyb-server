package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.wiyb.server.storage.database.entity.golf.detail.Iron
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractIron

data class IronDto(
    private val equipment: Iron
) : AbstractIron {
    data class IronLoftSpecDto(
        val number: String?,
        val loftDegree: String?,
        val lieAngle: String?
    )

    val produceType: String? = equipment.produceType
    val designType: String? = equipment.designType
    val composition: String? = makeComposition()
    val loft7Degree: String? = makeLoftDegree("7")
    val loftPDegree: String? = makeLoftDegree("P")
    val loftSpec: List<IronLoftSpecDto>? = makeLoftSpec()

    private fun makeComposition(): String? {
        val first = equipment.numbers?.find { it.isNotBlank() }
        val last = equipment.numbers?.findLast { it.isNotBlank() }

        if (equipment.numbers.isNullOrEmpty() || first == null) return null

        return "#$first - #$last"
    }

    private fun makeLoftDegree(number: String): String? {
        val index = equipment.numbers?.indexOf(number) ?: -1

        if (equipment.loftDegree.isNullOrEmpty() || index == -1) return null

        return "${equipment.loftDegree!![index]}°"
    }

    private fun makeLoftSpec(): List<IronLoftSpecDto>? {
        if (equipment.numbers.isNullOrEmpty() || equipment.loftDegree.isNullOrEmpty() || equipment.lieAngle.isNullOrEmpty()) return null

        return equipment.numbers!!.mapIndexed { index, number ->
            IronLoftSpecDto(
                number = number,
                loftDegree = equipment.loftDegree!![index],
                lieAngle = equipment.lieAngle!![index]
            )
        }
    }
}
