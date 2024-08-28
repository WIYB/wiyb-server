package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.wiyb.server.storage.database.entity.golf.detail.Driver
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractDriver

data class DriverDto(
    private val equipment: Driver
) : AbstractDriver {
    val volume: Float? = equipment.volume
    val isLoftChangeable: Boolean? = equipment.isLoftChangeable
    val isWeightChangeable: Boolean? = equipment.isWeightChangeable
    val loftSpec: List<String>? = equipment.loftDegree
}
