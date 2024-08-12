package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.wiyb.server.storage.database.entity.golf.detail.Wedge
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractWedge

data class WedgeDto(
    private val equipment: Wedge
) : AbstractWedge {
    val loftDegree: String? = equipment.loftDegree
    val produceType: String? = equipment.produceType
    val bounce: String? = equipment.bounce
    val grind: String? = equipment.grind
}
