package com.wiyb.server.storage.database.entity.golf.dto

import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType

data class EquipmentTypeDto(
    val name: String,
    val nameKo: String? = null
) {
    companion object {
        fun toDto(type: EquipmentType): EquipmentTypeDto = EquipmentTypeDto(type.getCode(), type.getCodeKo())

        fun toDtoList(): List<EquipmentTypeDto> = EquipmentType.entries.map { toDto(it) }
    }
}
