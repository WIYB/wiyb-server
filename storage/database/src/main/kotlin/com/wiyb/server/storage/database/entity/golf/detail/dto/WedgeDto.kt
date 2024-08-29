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
    val loftSpec: List<WedgeLoftSpecDto>? = makeLoftSpec()

    private fun makeLoftSpec(): List<WedgeLoftSpecDto>? {
        if (equipment.model.isNullOrEmpty() ||
            equipment.loftDegree.isNullOrEmpty() ||
            equipment.bounce.isNullOrEmpty() ||
            equipment.grind.isNullOrEmpty()
        ) {
            return null
        }

        return equipment.model!!.mapIndexed { index, model ->
            WedgeLoftSpecDto(
                number = model,
                loftDegree = equipment.loftDegree!![index],
                bounce = equipment.bounce!![index],
                grind = equipment.grind!![index]
            )
        }
    }
}
