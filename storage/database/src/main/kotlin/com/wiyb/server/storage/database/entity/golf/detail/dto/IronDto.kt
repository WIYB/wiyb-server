package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.wiyb.server.storage.database.entity.golf.detail.Iron
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractIron

data class IronDto(
    private val equipment: Iron
) : AbstractIron {
    data class IronLoftDetailDto(
        val number: String?,
        val loftDegree: String?,
        val lieAngle: String?
    )

    val numbers: List<String>? = equipment.numbers
    val produceType: String? = equipment.produceType
    val designType: String? = equipment.designType
    val loft7Degree: String? = equipment.loft7Degree
    val loftPDegree: String? = equipment.loftPDegree
    val loftSpecDetail: List<IronLoftDetailDto>? =
        equipment.loftDegree?.mapIndexed { index, s ->
            IronLoftDetailDto(
                number = equipment.numbers?.getOrNull(index),
                loftDegree = s,
                lieAngle = equipment.lieAngle?.getOrNull(index)
            )
        }
}
