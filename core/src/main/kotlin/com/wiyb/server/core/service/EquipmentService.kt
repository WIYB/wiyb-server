package com.wiyb.server.core.service

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.repository.golf.EquipmentRepository
import org.springframework.stereotype.Service

@Service
class EquipmentService(
    private val equipmentRepository: EquipmentRepository
) {
    fun findByNameKeyword(keyword: String): List<EquipmentSimpleDto> = equipmentRepository.findByNameKeyword(keyword)

    fun findOneWithDetailById(id: Long) =
        equipmentRepository.findOneWithDetailById(id) ?: throw CommonException(
            ErrorCode.PRODUCT_NOT_FOUND
        )
}
