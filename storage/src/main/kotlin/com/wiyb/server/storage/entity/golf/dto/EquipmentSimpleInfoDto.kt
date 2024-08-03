package com.wiyb.server.storage.entity.golf.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.entity.golf.constant.EquipmentType

data class EquipmentSimpleInfoDto
    @QueryProjection
    constructor(
        val id: String,
        val type: EquipmentType,
        val name: String,
        val releasedYear: String,
        val reviewCount: Long,
        val imageUrls: List<String>?
    )
