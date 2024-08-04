package com.wiyb.server.storage.database.repository.golf.custom

import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDetailDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto

interface EquipmentCustomRepository {
    fun findByNameKeyword(keyword: String): List<EquipmentSimpleDto>

    fun findOneWithDetailById(id: Long): EquipmentDetailDto?

//    fun bulkIncreaseViewCount()
}
