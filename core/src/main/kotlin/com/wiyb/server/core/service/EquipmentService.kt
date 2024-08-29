package com.wiyb.server.core.service

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.storage.database.entity.common.dto.SearchResultDto
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.EquipmentReview
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDto
import com.wiyb.server.storage.database.entity.user.User
import com.wiyb.server.storage.database.entity.user.UserEquipmentBookmark
import com.wiyb.server.storage.database.repository.golf.EquipmentRepository
import com.wiyb.server.storage.database.repository.golf.EquipmentReviewRepository
import com.wiyb.server.storage.database.repository.golf.detail.wrapper.EquipmentDetailRepositoryWrapper
import com.wiyb.server.storage.database.repository.user.UserEquipmentBookmarkRepository
import org.springframework.stereotype.Service

@Service
class EquipmentService(
    private val equipmentRepository: EquipmentRepository,
    private val equipmentReviewRepository: EquipmentReviewRepository,
    private val equipmentDetailRepositoryWrapper: EquipmentDetailRepositoryWrapper,
    private val userEquipmentBookmarkRepository: UserEquipmentBookmarkRepository
) {
    fun findSimpleById(id: Long): EquipmentSimpleDto =
        equipmentRepository.findSimpleById(id) ?: throw CommonException(ErrorCode.PRODUCT_NOT_FOUND)

    fun findOneById(id: Long): Equipment =
        equipmentRepository.findById(id).orElseThrow {
            CommonException(ErrorCode.PRODUCT_NOT_FOUND)
        }

    fun findReviewCounts(id: List<Long>): List<Long> = equipmentRepository.findReviewCounts(id)

    fun findById(idList: List<Long>): List<EquipmentSimpleDto> = equipmentRepository.findByIdList(idList)

    fun findOneWithDetailById(
        id: Long,
        type: EquipmentType
    ): EquipmentDto = equipmentDetailRepositoryWrapper.findDetailById(id, type) ?: throw CommonException(ErrorCode.PRODUCT_NOT_FOUND)

    fun findReviewByEquipmentId(id: Long) = equipmentReviewRepository.findByEquipmentId(id)

    fun findSimpleReviewByEquipmentId(id: Long) = equipmentReviewRepository.findSimpleByEquipmentId(id)

    fun findBySearchParameters(dto: SearchParameterDto): SearchResultDto<EquipmentSimpleDto> =
        equipmentRepository.findBySearchParameters(dto)

    fun findMostViewedProduct(type: EquipmentType?): List<EquipmentSimpleDto> = equipmentRepository.findMostViewedProduct(type)

    fun findBookmarkByUserAndEquipment(
        userId: Long,
        equipmentId: Long
    ): List<Long> = userEquipmentBookmarkRepository.findAllBookmarkIdByForeign(userId, equipmentId)

    fun isAlreadyReviewedByUser(
        equipmentId: Long,
        userId: Long
    ): Boolean = equipmentReviewRepository.existsByEquipmentIdAndUserId(equipmentId, userId)

    fun isAlreadyBookmarkedByUser(
        userId: Long,
        equipmentId: Long
    ): Boolean = userEquipmentBookmarkRepository.existsByUserIdAndEquipmentId(userId = userId, equipmentId = equipmentId)

    fun saveEquipment(equipment: Equipment) {
        equipmentRepository.save(equipment)
    }

    fun postProductReview(equipmentReview: EquipmentReview) {
        equipmentReviewRepository.save(equipmentReview)
    }

    fun bookmarkProduct(
        user: User,
        equipment: Equipment
    ) {
        userEquipmentBookmarkRepository.save(UserEquipmentBookmark(user, equipment))
    }

    fun unBookmarkProduct(id: List<Long>) {
        userEquipmentBookmarkRepository.deleteAllById(id)
    }
}
