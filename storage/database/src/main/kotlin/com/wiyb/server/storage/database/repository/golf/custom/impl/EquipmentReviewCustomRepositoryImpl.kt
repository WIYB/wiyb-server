package com.wiyb.server.storage.database.repository.golf.custom.impl

import com.querydsl.jpa.JPQLQuery
import com.wiyb.server.storage.database.entity.golf.EquipmentReview
import com.wiyb.server.storage.database.entity.golf.QEquipment.equipment
import com.wiyb.server.storage.database.entity.golf.QEquipmentReview.equipmentReview
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentReviewDto
import com.wiyb.server.storage.database.entity.golf.dto.QEquipmentReviewDto
import com.wiyb.server.storage.database.entity.user.QUser.user
import com.wiyb.server.storage.database.entity.user.QUserProfile.userProfile
import com.wiyb.server.storage.database.entity.user.dto.QUserSimpleProfileDto
import com.wiyb.server.storage.database.repository.golf.custom.EquipmentReviewCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class EquipmentReviewCustomRepositoryImpl :
    QuerydslRepositorySupport(EquipmentReview::class.java),
    EquipmentReviewCustomRepository {
    override fun findByEquipmentId(id: Long): List<EquipmentReviewDto> =
        findByEquipmentIdBase(id)
            .orderBy(equipmentReview.createdAt.desc())
            .fetch()

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
                    equipmentReview.content,
                    equipmentReview.imageUrls,
                    equipmentReview.createdAt,
                    equipmentReview.updatedAt,
                    QUserSimpleProfileDto(user, userProfile)
                )
            ).leftJoin(equipmentReview.equipment, equipment)
            .leftJoin(equipmentReview.user, user)
            .leftJoin(user.userProfile, userProfile)
            .where(equipment.id.eq(id))
}
