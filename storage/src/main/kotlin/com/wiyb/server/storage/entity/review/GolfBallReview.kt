package com.wiyb.server.storage.entity.review

import com.wiyb.server.storage.entity.golf.GolfBall
import com.wiyb.server.storage.entity.review.common.ReviewBaseEntity
import com.wiyb.server.storage.entity.user.User
import jakarta.persistence.Entity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "golf_ball_reviews")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE golf_ball_reviews SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class GolfBallReview(
    user: User,
    golfBall: GolfBall,
    evaluationMetric: Int,
    content: String,
    imageUrls: String?
) : ReviewBaseEntity<GolfBall>(user, golfBall, evaluationMetric, content, imageUrls)
