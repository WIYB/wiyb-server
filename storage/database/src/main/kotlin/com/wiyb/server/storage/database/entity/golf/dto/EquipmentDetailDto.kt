package com.wiyb.server.storage.database.entity.golf.dto

import com.wiyb.server.storage.database.entity.golf.EquipmentDetail
import com.wiyb.server.storage.database.entity.golf.constant.Difficulty
import com.wiyb.server.storage.database.entity.golf.constant.Grip

data class EquipmentDetailDto(
    // common
    val color: String? = null,
    val weight: Float? = null,
    // head
    val headProduceType: String? = null,
    val headDesignType: String? = null,
    val headNumber: Int? = null,
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
    val shaftTorque: Float? = null,
    val shaftTexture: String? = null,
    // grip
    val gripType: Grip? = null,
    val gripRound: Float? = null,
    // ball
    val ballPiece: Int? = null,
    val ballCover: String? = null
) {
    constructor(
        equipmentDetail: EquipmentDetail
    ) : this(
        equipmentDetail.color,
        equipmentDetail.weight,
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
        equipmentDetail.gripType,
        equipmentDetail.gripRound,
        equipmentDetail.ballPiece,
        equipmentDetail.ballCover
    )
}
