package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.GolfBaseEntity
import com.wiyb.server.storage.entity.constant.golf.Grip
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "club_grips")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE club_grips SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class ClubGrip(
    brand: Brand,
    name: String,
    gripType: Grip,
    releasedYear: String,
    round: Float,
    weight: Float,
    imageUrls: String? = null
) : GolfBaseEntity(name, releasedYear, imageUrls) {
    @Column(name = "grip_type", nullable = false)
    var gripType: Grip = gripType
        protected set

    @Column(name = "round", nullable = false)
    var round: Float = round
        protected set

    @Column(name = "weight", nullable = false)
    var weight: Float = weight
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipment", cascade = [CascadeType.REMOVE])
    protected val mutableReviews: MutableList<ClubGripReview> = mutableListOf()
    val reviews: List<ClubGripReview> get() = mutableReviews.toList()

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    var brand: Brand = brand
        protected set
}
