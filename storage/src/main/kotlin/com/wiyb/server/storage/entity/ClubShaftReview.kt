package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.ReviewBaseEntity
import jakarta.persistence.Entity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "club_shaft_reviews")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE club_shaft_reviews SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class ClubShaftReview(
    user: User,
    clubShaft: ClubShaft,
    evaluationMetric: Int,
    content: String,
    imageUrls: String?
) : ReviewBaseEntity<ClubShaft>(user, clubShaft, evaluationMetric, content, imageUrls)
