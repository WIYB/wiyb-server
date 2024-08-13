package com.wiyb.server.core.service

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.EquipmentReview
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDtoV2
import com.wiyb.server.storage.database.repository.golf.EquipmentRepository
import com.wiyb.server.storage.database.repository.golf.EquipmentReviewRepository
import com.wiyb.server.storage.database.repository.golf.detail.wrapper.EquipmentDetailRepositoryWrapper
import org.springframework.stereotype.Service

@Service
class EquipmentService(
    private val equipmentRepository: EquipmentRepository,
    private val equipmentReviewRepository: EquipmentReviewRepository,
    private val equipmentDetailRepositoryWrapper: EquipmentDetailRepositoryWrapper
) {
    fun findOneById(id: Long): Equipment =
        equipmentRepository.findById(id).orElseThrow {
            CommonException(ErrorCode.PRODUCT_NOT_FOUND)
        }

    fun findById(idList: List<Long>): List<EquipmentSimpleDto> = equipmentRepository.findByIdList(idList)

    fun findOneWithDetailById(
        id: Long,
        type: EquipmentType
    ): EquipmentDto = equipmentDetailRepositoryWrapper.findDetailById(id, type) ?: throw CommonException(ErrorCode.PRODUCT_NOT_FOUND)

    fun findReviewByEquipmentId(id: Long) = equipmentReviewRepository.findByEquipmentId(id)

    fun findSimpleReviewByEquipmentId(id: Long) = equipmentReviewRepository.findSimpleByEquipmentId(id)

    // todo: 통합 검색 method 통합 예정
    fun findBySearchParameters(dto: SearchParameterDto): List<EquipmentSimpleDto> =
        equipmentRepository.findBySearchParameters(
            dto.keyword,
            dto.filters,
            dto.sortedBy
        )

    fun findBySearchParametersV2(dto: SearchParameterDtoV2): List<EquipmentSimpleDto> = equipmentRepository.findBySearchParametersV2(dto)
    // =============================

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
