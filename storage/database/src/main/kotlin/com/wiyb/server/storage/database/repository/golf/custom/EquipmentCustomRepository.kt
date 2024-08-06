package com.wiyb.server.storage.database.repository.golf.custom

import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto

interface EquipmentCustomRepository {
    fun findByNameKeyword(keyword: String): List<EquipmentSimpleDto>

    fun findOneWithDetailById(id: Long): EquipmentDto?

//    fun bulkIncreaseViewCount()
}
