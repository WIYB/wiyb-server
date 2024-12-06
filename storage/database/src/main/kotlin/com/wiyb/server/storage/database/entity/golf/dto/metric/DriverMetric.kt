package com.wiyb.server.storage.database.entity.golf.dto.metric

import com.querydsl.core.annotations.QueryProjection

data class DriverMetric
    @QueryProjection
    constructor(
        override val forgiveness: Float,
        override val distance: Float,
        override val accuracy: Float,
        override val impactFeel: Float,
        override val impactSound: Float
    ) : BaseMetric() {
        override fun flatten(): List<Float> = listPad(forgiveness, distance, accuracy, impactFeel, impactSound)
    }
