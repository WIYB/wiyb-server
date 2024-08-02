package com.wiyb.server.storage.entity.golf

import com.wiyb.server.storage.entity.golf.common.GolfBaseEntity
import com.wiyb.server.storage.entity.review.GolfBallReview
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "golf_balls")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE golf_balls SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class GolfBall(
    brand: Brand,
    name: String,
    piece: Int,
    cover: String,
    releasedYear: String,
    imageUrls: String? = null
) : GolfBaseEntity<GolfBallReview>(brand, name, releasedYear, imageUrls) {
    @Column(name = "piece", nullable = false)
    var piece: Int = piece
        protected set

    @Column(name = "cover", nullable = false)
    var cover: String = cover
        protected set
}
