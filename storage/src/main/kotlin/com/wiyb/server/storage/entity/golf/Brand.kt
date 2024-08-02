package com.wiyb.server.storage.entity.golf

import com.wiyb.server.storage.entity.common.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "brands")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE brands SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class Brand(
    name: String,
    imageUrl: String?
) : BaseEntity() {
    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "image_url")
    var imageUrl: String? = imageUrl
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand", cascade = [CascadeType.REMOVE])
    protected val mutableClubHeads: MutableList<ClubHead> = mutableListOf()
    val clubHeads: List<ClubHead> get() = mutableClubHeads.toList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand", cascade = [CascadeType.REMOVE])
    protected val mutableClubShafts: MutableList<ClubShaft> = mutableListOf()
    val clubShafts: List<ClubShaft> get() = mutableClubShafts.toList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand", targetEntity = ClubGrip::class, cascade = [CascadeType.REMOVE])
    protected val mutableClubGrips: MutableList<ClubGrip> = mutableListOf()
    val clubGrips: List<ClubGrip> get() = mutableClubGrips.toList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand", cascade = [CascadeType.REMOVE])
    protected val mutableGolfBalls: MutableList<GolfBall> = mutableListOf()
    val golfBall: List<GolfBall> get() = mutableGolfBalls.toList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand", cascade = [CascadeType.REMOVE])
    protected val mutableGolfOtherEquipments: MutableList<GolfOtherEquipment> = mutableListOf()
    val golfOtherEquipment: List<GolfOtherEquipment> get() = mutableGolfOtherEquipments.toList()
}
