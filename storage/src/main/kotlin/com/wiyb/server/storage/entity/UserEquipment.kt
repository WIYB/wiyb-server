package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "user_equipments")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE user_equipments SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class UserEquipment(
    user: User,
    driverHead: ClubHead? = null,
    driverShaft: ClubShaft? = null,
    driverGrip: ClubGrip? = null,
    woodHead: ClubHead? = null,
    woodShaft: ClubShaft? = null,
    woodGrip: ClubGrip? = null,
    ironHead: ClubHead? = null,
    ironShaft: ClubShaft? = null,
    ironGrip: ClubGrip? = null,
    wedgeHead: ClubHead? = null,
    wedgeShaft: ClubShaft? = null,
    wedgeGrip: ClubGrip? = null,
    putterHead: ClubHead? = null,
    putterShaft: ClubShaft? = null,
    putterGrip: ClubGrip? = null,
    ball: GolfBall? = null
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = user
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_head_id")
    var driverHead: ClubHead? = driverHead
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_shaft_id")
    var driverShaft: ClubShaft? = driverShaft
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_grip_id")
    var driverGrip: ClubGrip? = driverGrip
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wood_head_id")
    var woodHead: ClubHead? = woodHead
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wood_shaft_id")
    var woodShaft: ClubShaft? = woodShaft
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wood_grip_id")
    var woodGrip: ClubGrip? = woodGrip
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "iron_head_id")
    var ironHead: ClubHead? = ironHead
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "iron_shaft_id")
    var ironShaft: ClubShaft? = ironShaft
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "iron_grip_id")
    var ironGrip: ClubGrip? = ironGrip
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wedge_head_id")
    var wedgeHead: ClubHead? = wedgeHead
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wedge_shaft_id")
    var wedgeShaft: ClubShaft? = wedgeShaft
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wedge_grip_id")
    var wedgeGrip: ClubGrip? = wedgeGrip
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "putter_head_id")
    var putterHead: ClubHead? = putterHead
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "putter_shaft_id")
    var putterShaft: ClubShaft? = putterShaft
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "putter_grip_id")
    var putterGrip: ClubGrip? = putterGrip
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ball_id")
    var ball: GolfBall? = ball
        protected set
}
