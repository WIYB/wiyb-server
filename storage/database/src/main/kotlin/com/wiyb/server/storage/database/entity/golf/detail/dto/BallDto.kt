package com.wiyb.server.storage.database.entity.golf.detail.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.database.entity.golf.detail.Ball
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractBall

data class BallDto
    @QueryProjection
    constructor(
        private val equipment: Ball
    ) : AbstractBall {
        val piece: String? = equipment.piece
        val spin: String? = equipment.spin
        val launch: String? = equipment.launch
        val feel: String? = equipment.feel
        val dimple: String? = equipment.dimple
        val texture: String? = equipment.texture
    }
