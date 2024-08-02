package com.wiyb.server.storage.entity.review

import com.wiyb.server.storage.entity.golf.GolfOtherEquipment
import com.wiyb.server.storage.entity.review.common.ReviewBaseEntity
import com.wiyb.server.storage.entity.user.User
import jakarta.persistence.Entity
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
) : ReviewBaseEntity<GolfOtherEquipment>(user, golfOtherEquipment, evaluationMetric, content, imageUrls)
