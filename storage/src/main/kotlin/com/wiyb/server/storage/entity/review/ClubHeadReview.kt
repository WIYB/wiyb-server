package com.wiyb.server.storage.entity.review

import com.wiyb.server.storage.entity.golf.ClubHead
import com.wiyb.server.storage.entity.review.common.ReviewBaseEntity
import com.wiyb.server.storage.entity.user.User
import jakarta.persistence.Entity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "club_head_reviews")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE club_head_reviews SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class ClubHeadReview(
    user: User,
    clubHead: ClubHead,
    evaluationMetric: Int,
    content: String,
    imageUrls: String?
) : ReviewBaseEntity<ClubHead>(user, clubHead, evaluationMetric, content, imageUrls)
