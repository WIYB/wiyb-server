package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.wiyb.server.storage.database.entity.golf.detail.Putter
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractPutter

data class PutterDto(
    private val equipment: Putter
) : AbstractPutter {
    val loftDegree: String? = equipment.loftDegree
    val weight: String? = equipment.weight
    val neckShape: String? = equipment.neckShape
}
