package com.wiyb.server.storage.database.entity.golf.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType

data class EquipmentSimpleDto
    @QueryProjection
    constructor(
        val id: String,
        val brand: String,
        val type: EquipmentType,
        val name: String,
        val viewCount: Long,
        val reviewCount: Long,
        val averageScore: Float,
        val releasedYear: String?,
        val imageUrls: List<String>?
    )
