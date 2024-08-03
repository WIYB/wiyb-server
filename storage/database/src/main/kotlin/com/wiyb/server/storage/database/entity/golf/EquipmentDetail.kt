package com.wiyb.server.storage.database.entity.golf

import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.constant.Difficulty
import com.wiyb.server.storage.database.entity.golf.constant.Grip
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "equipment_details")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE equipment_details SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class EquipmentDetail(
    equipment: Equipment,
    // common
    color: String? = null,
    weight: Float? = null,
    // head
    headProduceType: String? = null,
    headDesignType: String? = null,
    headNumber: Int? = null,
    headShape: String? = null,
    headDifficulty: Difficulty? = null,
    headLoftDegree: String? = null,
    driverVolume: Float? = null,
    iron7LoftDegree: String? = null,
    ironPLoftDegree: String? = null,
    putterNeckShape: String? = null,
    // shaft
    shaftStrength: String? = null,
    shaftKickPoint: String? = null,
    shaftTorque: Float? = null,
    shaftTexture: String? = null,
    // grip
    gripType: Grip? = null,
    gripRound: Float? = null,
    // ball
    ballPiece: Int? = null,
    ballCover: String? = null
) : BaseEntity() {
    @Column(name = "color")
    var color: String? = color
        protected set

    @Column(name = "weight")
    var weight: Float? = weight
        protected set

    @Column(name = "head_produce_type")
    var headProduceType: String? = headProduceType
        protected set

    @Column(name = "head_design_type")
    var headDesignType: String? = headDesignType
        protected set

    @Column(name = "head_number")
    var headNumber: Int? = headNumber
        protected set

    @Column(name = "head_shape")
    var headShape: String? = headShape
        protected set

    @Column(name = "head_difficulty")
    var headDifficulty: Difficulty? = headDifficulty
        protected set

    @Column(name = "head_loft_degree")
    var headLoftDegree: String? = headLoftDegree
        protected set

    @Column(name = "driver_volume")
    var driverVolume: Float? = driverVolume
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

    @Column(name = "shaft_strength")
    var shaftStrength: String? = shaftStrength
        protected set

    @Column(name = "shaft_kick_point")
    var shaftKickPoint: String? = shaftKickPoint
        protected set

    @Column(name = "shaft_torque")
    var shaftTorque: Float? = shaftTorque
        protected set

    @Column(name = "shaft_texture")
    var shaftTexture: String? = shaftTexture
        protected set

    @Column(name = "grip_type")
    var gripType: Grip? = gripType
        protected set

    @Column(name = "grip_round")
    var gripRound: Float? = gripRound
        protected set

    @Column(name = "ball_piece")
    var ballPiece: Int? = ballPiece
        protected set

    @Column(name = "ball_cover")
    var ballCover: String? = ballCover
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    var equipment: Equipment = equipment
        protected set
}
