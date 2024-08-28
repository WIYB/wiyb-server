package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.wiyb.server.storage.database.entity.golf.detail.Wedge
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractWedge

data class WedgeDto(
    private val equipment: Wedge
) : AbstractWedge {
    data class WedgeLoftSpecDto(
        val number: String?,
        val loftDegree: String?,
        val bounce: String?,
        val grind: String?
    )

    val produceType: String? = equipment.produceType
    val loftSpec: List<WedgeLoftSpecDto>? =
        equipment.loftDegree?.mapIndexed { index, s ->
            WedgeLoftSpecDto(
                number = equipment.model?.getOrNull(index),
                loftDegree = s,
                bounce = equipment.bounce?.getOrNull(index),
                grind = equipment.grind?.getOrNull(index)
            )
        }
}
