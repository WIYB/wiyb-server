package com.wiyb.server.storage.database.repository.user.custom.impl

import com.wiyb.server.storage.database.entity.golf.QEquipmentReview.equipmentReview
import com.wiyb.server.storage.database.entity.user.QUserEquipmentReviewLike.userEquipmentReviewLike
import com.wiyb.server.storage.database.entity.user.UserEquipmentReviewLike
import com.wiyb.server.storage.database.repository.user.custom.UserEquipmentReviewLikeCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class UserEquipmentReviewLikeCustomRepositoryImpl :
    QuerydslRepositorySupport(UserEquipmentReviewLike::class.java),
    UserEquipmentReviewLikeCustomRepository {
    override fun existsByForeign(
        userId: Long,
        equipmentReviewId: Long
    ): Boolean {
        val result =
            from(userEquipmentReviewLike)
                .select(userEquipmentReviewLike.id)
                .where(
                    userEquipmentReviewLike.user.id
                        .eq(userId)
                        .and(userEquipmentReviewLike.equipmentReview.id.`in`(equipmentReviewId))
                ).fetchFirst()

        return result != null
    }

    override fun findAllLikeIdByForeign(
        userId: Long,
        equipmentReviewIds: List<Long>
    ): List<Long> =
        from(userEquipmentReviewLike)
            .select(equipmentReview.id)
            .where(
                userEquipmentReviewLike.user.id
                    .eq(userId)
                    .and(userEquipmentReviewLike.equipmentReview.id.`in`(equipmentReviewIds))
            ).fetch()

    override fun findAllLikeIdByForeign(
        userId: Long,
        equipmentReviewId: Long
    ): List<Long> =
        from(userEquipmentReviewLike)
            .select(userEquipmentReviewLike.id)
            .where(
                userEquipmentReviewLike.user.id
                    .eq(userId)
                    .and(userEquipmentReviewLike.equipmentReview.id.eq(equipmentReviewId))
            ).fetch()
}
