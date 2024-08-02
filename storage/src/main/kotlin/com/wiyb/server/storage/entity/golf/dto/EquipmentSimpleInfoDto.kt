package com.wiyb.server.storage.entity.golf.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.entity.golf.Equipment
import com.wiyb.server.storage.entity.golf.constant.EquipmentType

data class EquipmentSimpleInfoDto
    @QueryProjection
    constructor(
        private val equipment: Equipment,
        val brand: String,
        val reviewCount: Long
    ) {
        val id: String = equipment.id.toString()
        val type: EquipmentType = equipment.type
        val name: String = equipment.name
        val releasedYear: String = equipment.releasedYear
        val imageUrls: List<String>? = equipment.imageUrls
    }
