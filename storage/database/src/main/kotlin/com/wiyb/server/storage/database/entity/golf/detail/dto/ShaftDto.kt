package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.wiyb.server.storage.database.entity.golf.detail.Shaft
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractShaft

data class ShaftDto(
    private val equipment: Shaft
) : AbstractShaft {
    val weight: String? = equipment.weight
    val strength: String? = equipment.strength
    val kickPoint: String? = equipment.kickPoint
    val torque: String? = equipment.torque
    val texture: String? = equipment.texture
    val tipDiameter: String? = equipment.tipDiameter
    val buttDiameter: String? = equipment.buttDiameter
    val spin: String? = equipment.spin
    val launch: String? = equipment.launch
}
