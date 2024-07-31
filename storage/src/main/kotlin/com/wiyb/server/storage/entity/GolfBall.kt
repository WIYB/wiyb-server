package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.GolfBaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "golf_balls")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE golf_balls SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class GolfBall(
    brand: String,
    name: String,
    piece: Int,
    cover: String,
    releasedYear: String,
    imageUrls: String? = null
) : GolfBaseEntity(brand, name, releasedYear, imageUrls) {
    @Column(name = "piece", nullable = false)
    var piece: Int = piece
        protected set

    @Column(name = "cover", nullable = false)
    var cover: String = cover
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "golf_balls", cascade = [CascadeType.REMOVE])
    protected val mutableGolfBallReviews: MutableList<GolfBallReview> = mutableListOf()
    val golfBallReviews: List<GolfBallReview> get() = mutableGolfBallReviews.toList()
}
