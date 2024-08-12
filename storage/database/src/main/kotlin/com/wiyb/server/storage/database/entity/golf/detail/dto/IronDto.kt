package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.wiyb.server.storage.database.entity.golf.detail.Iron
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractIron

data class IronDto(
    private val equipment: Iron
) : AbstractIron {
    val loftDegree: String? = equipment.loftDegree
    val numbers: String? = equipment.numbers
    val produceType: String? = equipment.produceType
    val designType: String? = equipment.designType
    val loft7Degree: String? = equipment.loft7Degree
    val loftPDegree: String? = equipment.loftPDegree
}
