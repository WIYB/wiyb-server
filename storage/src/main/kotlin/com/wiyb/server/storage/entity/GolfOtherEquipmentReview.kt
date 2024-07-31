package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.ReviewBaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "golf_other_equipment_reviews")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE golf_other_equipment_reviews SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class GolfOtherEquipmentReview(
    user: User,
    golfOtherEquipment: GolfOtherEquipment,
    evaluationMetric: Int,
    content: String,
    imageUrls: String?
) : ReviewBaseEntity<GolfOtherEquipment>(evaluationMetric, content, imageUrls) {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = user
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    var equipment: GolfOtherEquipment = golfOtherEquipment
        protected set
}
