package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.wiyb.server.storage.database.entity.golf.detail.Hybrid
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractHybrid

data class HybridDto(
    private val equipment: Hybrid
) : AbstractHybrid {
    val loftDegree: String? = equipment.loftDegree
    val numbers: String? = equipment.numbers
}
