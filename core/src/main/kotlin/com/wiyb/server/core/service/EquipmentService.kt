package com.wiyb.server.core.service

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.EquipmentReview
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.repository.golf.EquipmentRepository
import com.wiyb.server.storage.database.repository.golf.EquipmentReviewRepository
import org.springframework.stereotype.Service

@Service
class EquipmentService(
    private val equipmentRepository: EquipmentRepository,
    private val equipmentReviewRepository: EquipmentReviewRepository
) {
    fun findOneById(id: Long): Equipment =
        equipmentRepository.findById(id).orElseThrow {
            CommonException(ErrorCode.PRODUCT_NOT_FOUND)
        }

    fun findByNameKeyword(keyword: String): List<EquipmentSimpleDto> = equipmentRepository.findByNameKeyword(keyword)

    fun findOneWithDetailById(id: Long) =
        equipmentRepository.findOneWithDetailById(id) ?: throw CommonException(
            ErrorCode.PRODUCT_NOT_FOUND
        )

    fun findReviewByEquipmentId(id: Long) = equipmentReviewRepository.findByEquipmentId(id)

    fun findSimpleReviewByEquipmentId(id: Long) = equipmentReviewRepository.findSimpleByEquipmentId(id)

    fun isAlreadyReviewedByUser(
        equipmentId: Long,
        userId: Long
    ): Boolean = equipmentReviewRepository.existsByEquipmentIdAndUserId(equipmentId, userId)

    fun saveEquipment(equipment: Equipment) {
        equipmentRepository.save(equipment)
    }

    fun postProductReview(equipmentReview: EquipmentReview) {
        equipmentReviewRepository.save(equipmentReview)
    }
}
