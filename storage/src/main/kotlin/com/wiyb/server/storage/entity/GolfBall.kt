package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.GolfBaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
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
) : GolfBaseEntity(name, releasedYear, imageUrls) {
    @Column(name = "piece", nullable = false)
    var piece: Int = piece
        protected set

    @Column(name = "cover", nullable = false)
    var cover: String = cover
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipment", cascade = [CascadeType.REMOVE])
    protected val mutableReviews: MutableList<GolfBallReview> = mutableListOf()
    val reviews: List<GolfBallReview> get() = mutableReviews.toList()

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    var brand: Brand = brand
        protected set
}
