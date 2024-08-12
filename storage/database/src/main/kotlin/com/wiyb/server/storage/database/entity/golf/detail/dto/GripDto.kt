package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.wiyb.server.storage.database.entity.golf.detail.Grip
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractGrip

data class GripDto(
    private val equipment: Grip
) : AbstractGrip {
    val weight: String? = equipment.weight
    val size: String? = equipment.size
    val coreSize: String? = equipment.coreSize
    val feel: String? = equipment.feel
    val material: String? = equipment.material
    val torque: String? = equipment.torque
    val diameter: String? = equipment.diameter
}
