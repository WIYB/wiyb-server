package com.wiyb.server.storage.database.repository.golf.custom.impl

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.jpa.JPQLQuery
import com.wiyb.server.storage.database.entity.common.dto.PaginationResultDto
import com.wiyb.server.storage.database.entity.golf.EquipmentReview
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.QEquipmentReview.equipmentReview
import com.wiyb.server.storage.database.entity.golf.constant.ReviewSortedBy
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentReviewDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentReviewDto
import com.wiyb.server.storage.database.entity.golf.dto.ReviewPaginationDto
import com.wiyb.server.storage.database.entity.user.QUser.user
import com.wiyb.server.storage.database.entity.user.QUserProfile.userProfile
import com.wiyb.server.storage.database.entity.user.dto.QUserSimpleProfileDto
import com.wiyb.server.storage.database.repository.golf.custom.EquipmentReviewCustomRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class EquipmentReviewCustomRepositoryImpl :
    QuerydslRepositorySupport(EquipmentReview::class.java),
    EquipmentReviewCustomRepository {
    override fun findWithPagination(parameter: ReviewPaginationDto): PaginationResultDto<EquipmentReviewDto> {
        val pageRequest = parameter.of()
        val query = findByEquipmentIdBase(parameter.equipmentId)

        query
            .groupBy(equipmentReview.id)
            .offset(pageRequest.offset)
            .limit(pageRequest.pageSize.toLong())
            .orderBy(*orderStrategy(parameter.sortedBy))

        val page = PageImpl(query.fetch(), pageRequest, query.fetchCount())

        return PaginationResultDto.fromPage<EquipmentReviewDto>(parameter.contextId, page)
    }

    override fun findSimpleByEquipmentId(id: Long): List<EquipmentReviewDto> =
        findByEquipmentIdBase(id)
            .limit(3)
            .orderBy(equipmentReview.createdAt.desc())
            .fetch()

    private fun findByEquipmentIdBase(id: Long): JPQLQuery<EquipmentReviewDto> =
        from(equipmentReview)
            .select(
                QEquipmentReviewDto(
                    equipmentReview.id.stringValue(),
                    equipmentReview.likeCount,
                    equipmentReview.content,
                    equipmentReview.imageUrls,
                    equipmentReview.createdAt,
                    equipmentReview.updatedAt,
                    QUserSimpleProfileDto(
                        user.id.stringValue(),
                        userProfile.nickname,
                        userProfile.handy,
                        userProfile.height,
                        userProfile.weight,
                        userProfile.imageUrl
                    )
                )
            ).leftJoin(equipmentReview.equipment, equipment)
            .leftJoin(equipmentReview.user, user)
            .leftJoin(user.userProfile, userProfile)
            .where(equipment.id.eq(id))

    private fun orderStrategy(sort: ReviewSortedBy): Array<OrderSpecifier<Long>> =
        when (sort) {
            ReviewSortedBy.CREATED_ASC ->
                arrayOf(equipmentReview.id.castToNum(Long::class.java).asc())

            ReviewSortedBy.LIKE_COUNT_DESC ->
                arrayOf(
                    equipmentReview.likeCount.castToNum(Long::class.java).desc(),
                    equipmentReview.id.castToNum(Long::class.java).desc()
                )

            else ->
                arrayOf(equipmentReview.id.castToNum(Long::class.java).desc())
        }
}
