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
    val loftSpec: List<IronLoftSpecDto>? =
        equipment.loftDegree?.mapIndexed { index, s ->
            IronLoftSpecDto(
                number = equipment.numbers?.getOrNull(index),
                loftDegree = s,
                lieAngle = equipment.lieAngle?.getOrNull(index)
            )
        }
}
