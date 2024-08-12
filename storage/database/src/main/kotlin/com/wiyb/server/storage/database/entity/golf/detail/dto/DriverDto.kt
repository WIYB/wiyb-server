package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.wiyb.server.storage.database.entity.golf.detail.Driver
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractDriver

data class DriverDto(
    private val equipment: Driver
) : AbstractDriver {
    val loftDegree: String? = equipment.loftDegree
    val volume: Float? = equipment.volume
}
