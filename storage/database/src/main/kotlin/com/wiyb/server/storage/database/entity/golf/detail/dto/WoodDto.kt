package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.wiyb.server.storage.database.entity.golf.detail.Wood
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractWood

data class WoodDto(
    private val equipment: Wood
) : AbstractWood {
    val loftDegree: String? = equipment.loftDegree
    val numbers: String? = equipment.numbers
}
