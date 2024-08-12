package com.wiyb.server.storage.database.repository.golf.detail.custom

import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto

interface EquipmentDetailCustomRepository<T> {
    fun findDetailById(id: Long): EquipmentDto?
}
