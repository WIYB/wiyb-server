package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.ReviewBaseEntity
import jakarta.persistence.Entity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "club_grip_reviews")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE club_grip_reviews SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class ClubGripReview(
    user: User,
    clubGrip: ClubGrip,
    evaluationMetric: Int,
    content: String,
    imageUrls: String?
) : ReviewBaseEntity<ClubGrip>(user, clubGrip, evaluationMetric, content, imageUrls)
