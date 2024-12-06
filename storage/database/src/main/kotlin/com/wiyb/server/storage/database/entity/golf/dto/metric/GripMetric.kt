package com.wiyb.server.storage.database.entity.golf.dto.metric

import com.querydsl.core.annotations.QueryProjection

data class GripMetric
    @QueryProjection
    constructor(
        override val touch: Float,
        override val gripComfort: Float,
        override val durability: Float
    ) : BaseMetric() {
        override fun flatten(): List<Float> = listPad(touch, gripComfort, durability)
    }
