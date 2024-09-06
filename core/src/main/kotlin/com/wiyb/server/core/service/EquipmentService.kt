package com.wiyb.server.core.service

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.core.domain.product.PopularProductByMetricQuery
import com.wiyb.server.storage.database.entity.common.dto.PaginationResultDto
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.EquipmentEvaluatedMetric
import com.wiyb.server.storage.database.entity.golf.EquipmentReview
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentReviewDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.ReviewPaginationDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDto
import com.wiyb.server.storage.database.entity.golf.dto.metric.constant.EvaluationType
import com.wiyb.server.storage.database.entity.user.User
import com.wiyb.server.storage.database.entity.user.UserEquipmentBookmark
import com.wiyb.server.storage.database.entity.user.UserEquipmentReviewLike
import com.wiyb.server.storage.database.repository.golf.EquipmentEvaluatedMetricRepository
import com.wiyb.server.storage.database.repository.golf.EquipmentRepository
import com.wiyb.server.storage.database.repository.golf.EquipmentReviewRepository
import com.wiyb.server.storage.database.repository.golf.detail.wrapper.EquipmentDetailRepositoryWrapper
import com.wiyb.server.storage.database.repository.user.UserEquipmentBookmarkRepository
import com.wiyb.server.storage.database.repository.user.UserEquipmentReviewLikeRepository
import org.springframework.stereotype.Service

@Service
class EquipmentService(
    private val equipmentRepository: EquipmentRepository,
    private val equipmentReviewRepository: EquipmentReviewRepository,
    private val equipmentDetailRepositoryWrapper: EquipmentDetailRepositoryWrapper,
    private val userEquipmentReviewLikeRepository: UserEquipmentReviewLikeRepository,
    private val userEquipmentBookmarkRepository: UserEquipmentBookmarkRepository,
    private val equipmentEvaluatedMetricRepository: EquipmentEvaluatedMetricRepository
) {
    fun findSimpleById(id: Long): EquipmentSimpleDto =
        equipmentRepository.findSimpleById(id) ?: throw CommonException(ErrorCode.PRODUCT_NOT_FOUND)

    fun findOneById(id: Long): Equipment =
        equipmentRepository.findById(id).orElseThrow {
            CommonException(ErrorCode.PRODUCT_NOT_FOUND)
        }

    fun findByIdWithMetric(id: Long): Equipment =
        equipmentRepository.findByIdWithMetric(id).orElseThrow {
            CommonException(ErrorCode.PRODUCT_NOT_FOUND)
        }

    fun findPopularByMetric(query: PopularProductByMetricQuery): List<EquipmentSimpleDto> =
        equipmentRepository.findTopScoreByMetric(
            EquipmentType.fromCode(query.type),
            EvaluationType.fromCode(query.metric),
            query.size
        )

    fun findReviewCounts(id: List<Long>): List<Long> = equipmentRepository.findReviewCounts(id)

    fun findById(idList: List<Long>): List<EquipmentSimpleDto> = equipmentRepository.findByIdList(idList)

    fun findOneWithDetailById(
        id: Long,
        type: EquipmentType
    ): EquipmentDto = equipmentDetailRepositoryWrapper.findDetailById(id, type) ?: throw CommonException(ErrorCode.PRODUCT_NOT_FOUND)

    fun findReviewWithPagination(parameter: ReviewPaginationDto): PaginationResultDto<EquipmentReviewDto> =
        equipmentReviewRepository.findWithPagination(parameter)

    fun findReviewById(id: Long) = equipmentReviewRepository.findFirstById(id) ?: throw CommonException(ErrorCode.REVIEW_NOT_FOUND)

    fun findSimpleReviewByEquipmentId(id: Long) = equipmentReviewRepository.findSimpleByEquipmentId(id)

    fun findBySearchParameters(dto: SearchParameterDto): PaginationResultDto<EquipmentSimpleDto> =
        equipmentRepository.findBySearchParameters(dto)

    fun findMostViewedProduct(type: EquipmentType?): List<EquipmentSimpleDto> = equipmentRepository.findMostViewedProduct(type)

    fun findLikeByForeign(
        userId: Long,
        equipmentReviewIds: List<Long>
    ): List<Long> = userEquipmentReviewLikeRepository.findAllLikeIdByForeign(userId, equipmentReviewIds)

    fun findLikeByForeign(
        userId: Long,
        equipmentReviewId: Long
    ): List<Long> = userEquipmentReviewLikeRepository.findAllLikeIdByForeign(userId, equipmentReviewId)

    fun findBookmarkByUserAndEquipment(
        userId: Long,
        equipmentId: Long
    ): List<Long> = userEquipmentBookmarkRepository.findAllBookmarkIdByForeign(userId, equipmentId)

    fun isAlreadyReviewedByUser(
        equipmentId: Long,
        userId: Long
    ): Boolean = equipmentReviewRepository.existsByEquipmentIdAndUserId(equipmentId, userId)

    fun isAlreadyLikedReview(
        userId: Long,
        equipmentReviewId: Long
    ): Boolean = userEquipmentReviewLikeRepository.existsByForeign(userId, equipmentReviewId)

    fun isAlreadyBookmarkedByUser(
        userId: Long,
        equipmentId: Long
    ): Boolean = userEquipmentBookmarkRepository.existsByUserIdAndEquipmentId(userId = userId, equipmentId = equipmentId)

    fun saveEquipment(equipment: Equipment) {
        equipmentRepository.save(equipment)
    }

    fun saveEquipmentEvaluatedMetric(metric: EquipmentEvaluatedMetric) {
        equipmentEvaluatedMetricRepository.save(metric)
    }

    fun postProductReview(equipmentReview: EquipmentReview) {
        equipmentReviewRepository.save(equipmentReview)
    }

    fun increaseReviewLikeCount(review: EquipmentReview) {
        review.increaseLikeCount()
        equipmentReviewRepository.save(review)
    }

    fun decreaseReviewLikeCount(review: EquipmentReview) {
        review.decreaseLikeCount()
        equipmentReviewRepository.save(review)
    }

    fun likeProductReview(
        user: User,
        review: EquipmentReview
    ) {
        userEquipmentReviewLikeRepository.save(UserEquipmentReviewLike(user, review))
    }

    fun unlikeProductReview(id: List<Long>) {
        userEquipmentReviewLikeRepository.deleteAllById(id)
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
