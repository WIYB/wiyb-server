package com.wiyb.server.core.service

import com.wiyb.server.storage.entity.golf.dto.EquipmentSimpleInfoDto
import com.wiyb.server.storage.repository.golf.EquipmentRepository
import org.springframework.stereotype.Service

@Service
class EquipmentService(
    private val equipmentRepository: EquipmentRepository
) {
    fun findByNameKeyword(keyword: String): List<EquipmentSimpleInfoDto> = equipmentRepository.findByNameKeyword(keyword)
}
