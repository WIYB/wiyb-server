package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.GolfBaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "club_shafts")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE club_shafts SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class ClubShaft(
    brand: String,
    name: String,
    strength: String,
    weight: Float,
    kickPoint: String,
    torque: Float,
    texture: String,
    releasedYear: String,
    imageUrls: String? = null
) : GolfBaseEntity(brand, name, releasedYear, imageUrls) {
    @Column(name = "strength", nullable = false)
    var strength: String = strength
        protected set

    @Column(name = "weight", nullable = false)
    var weight: Float = weight
        protected set

    @Column(name = "kick_point", nullable = false)
    var kickPoint: String = kickPoint
        protected set

    @Column(name = "torque", nullable = false)
    var torque: Float = torque
        protected set

    @Column(name = "texture", nullable = false)
    var texture: String = texture
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipment", cascade = [CascadeType.REMOVE])
    protected val mutableClubShaftReviews: MutableList<ClubShaftReview> = mutableListOf()
    val clubShaftReviews: List<ClubShaftReview> get() = mutableClubShaftReviews.toList()
}
