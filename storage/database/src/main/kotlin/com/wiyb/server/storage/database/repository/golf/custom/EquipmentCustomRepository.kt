package com.wiyb.server.storage.database.repository.golf.custom

import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleInfoDto

interface EquipmentCustomRepository {
    fun findByNameKeyword(keyword: String): List<EquipmentSimpleInfoDto>
}
