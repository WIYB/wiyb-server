package com.wiyb.server.storage.repository.golf.custom

import com.wiyb.server.storage.entity.golf.dto.EquipmentSimpleInfoDto

interface EquipmentCustomRepository {
    fun findByNameKeyword(keyword: String): List<EquipmentSimpleInfoDto>
}
