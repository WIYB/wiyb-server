package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.GolfBaseEntity
import com.wiyb.server.storage.entity.constant.golf.Difficulty
import com.wiyb.server.storage.entity.constant.golf.Head
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "club_heads")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE club_heads SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class ClubHead(
    brand: String,
    name: String,
    headType: Head,
    produceType: String,
    designType: String,
    headShape: String,
    color: String,
    weight: Float,
    difficulty: Difficulty,
    loftDegree: String,
    releasedYear: String,
    imageUrls: String?,
    driverVolume: Float? = null,
    ironNumber: Int? = null,
    iron7LoftDegree: String? = null,
    ironPLoftDegree: String? = null,
    putterNeckShape: String? = null
) : GolfBaseEntity(brand, name, releasedYear, imageUrls) {
    @Column(name = "head_type", nullable = false)
    var headType: Head = headType
        protected set

    @Column(name = "produce_type", nullable = false)
    var produceType: String = produceType
        protected set

    @Column(name = "design_type", nullable = false)
    var designType: String = designType
        protected set

    @Column(name = "head_shape", nullable = false)
    var headShape: String = headShape
        protected set

    @Column(name = "color", nullable = false)
    var color: String = color
        protected set

    @Column(name = "weight", nullable = false)
    var weight: Float = weight
        protected set

    @Column(name = "difficulty", nullable = false)
    var difficulty: Difficulty = difficulty
        protected set

    @Column(name = "loft_degree", nullable = false)
    var loftDegree: String = loftDegree
        protected set

    @Column(name = "driver_volume")
    var driverVolume: Float? = driverVolume
        protected set

    @Column(name = "iron_number")
    var ironNumber: Int? = ironNumber
        protected set

    @Column(name = "iron_7_loft_degree")
    var iron7LoftDegree: String? = iron7LoftDegree
        protected set

    @Column(name = "iron_p_loft_degree")
    var ironPLoftDegree: String? = ironPLoftDegree
        protected set

    @Column(name = "putter_neck_shape")
    var putterNeckShape: String? = putterNeckShape
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipment", cascade = [CascadeType.REMOVE])
    protected val mutableClubHeadReviews: MutableList<ClubHeadReview> = mutableListOf()
    val clubHeadReviews: List<ClubHeadReview> get() = mutableClubHeadReviews.toList()
}
