package com.wiyb.server.storage.database.entity.golf.dto

import com.wiyb.server.storage.database.entity.golf.EquipmentDetail
import com.wiyb.server.storage.database.entity.golf.constant.Difficulty
import com.wiyb.server.storage.database.entity.golf.constant.Grip
import com.wiyb.server.storage.database.entity.user.constant.Gender

data class EquipmentDetailDto(
    // common
    val color: String? = null,
    val weight: String? = null,
    val launch: String? = null,
    val spin: String? = null,
    val gender: Gender? = null,
    val bounce: String? = null,
    val grind: String? = null,
    // head
    val headProduceType: String? = null,
    val headDesignType: String? = null,
    val headNumber: String? = null,
    val headShape: String? = null,
    val headDifficulty: Difficulty? = null,
    val headLoftDegree: String? = null,
    val driverVolume: Float? = null,
    val iron7LoftDegree: String? = null,
    val ironPLoftDegree: String? = null,
    val putterNeckShape: String? = null,
    // shaft
    val shaftStrength: String? = null,
    val shaftKickPoint: String? = null,
    val shaftTorque: String? = null,
    val shaftTexture: String? = null,
    val shaftTipDiameter: String? = null,
    val shaftButtDiameter: String? = null,
    val shaftBend: String? = null,
    // grip
    val gripType: Grip? = null,
    val gripRound: Float? = null,
    // ball
    val ballPiece: Int? = null,
    val ballCover: String? = null,
    val ballFeel: String? = null,
    val ballDimple: String? = null
) {
    constructor(
        equipmentDetail: EquipmentDetail
    ) : this(
        equipmentDetail.color,
        equipmentDetail.weight,
        equipmentDetail.launch,
        equipmentDetail.spin,
        equipmentDetail.gender,
        equipmentDetail.bounce,
        equipmentDetail.grind,
        equipmentDetail.headProduceType,
        equipmentDetail.headDesignType,
        equipmentDetail.headNumber,
        equipmentDetail.headShape,
        equipmentDetail.headDifficulty,
        equipmentDetail.headLoftDegree,
        equipmentDetail.driverVolume,
        equipmentDetail.iron7LoftDegree,
        equipmentDetail.ironPLoftDegree,
        equipmentDetail.putterNeckShape,
        equipmentDetail.shaftStrength,
        equipmentDetail.shaftKickPoint,
        equipmentDetail.shaftTorque,
        equipmentDetail.shaftTexture,
        equipmentDetail.shaftTipDiameter,
        equipmentDetail.shaftButtDiameter,
        equipmentDetail.shaftBend,
        equipmentDetail.gripType,
        equipmentDetail.gripRound,
        equipmentDetail.ballPiece,
        equipmentDetail.ballCover,
        equipmentDetail.ballFeel,
        equipmentDetail.ballDimple
    )
}
