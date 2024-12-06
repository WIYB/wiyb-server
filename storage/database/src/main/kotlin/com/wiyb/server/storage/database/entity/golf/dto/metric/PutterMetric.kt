package com.wiyb.server.storage.database.entity.golf.dto.metric

import com.querydsl.core.annotations.QueryProjection

data class PutterMetric
    @QueryProjection
    constructor(
        override val forgiveness: Float,
        override val accuracy: Float,
        override val distanceControl: Float,
        override val impactFeel: Float
    ) : BaseMetric() {
        override fun flatten(): List<Float> = listPad(forgiveness, accuracy, distanceControl, impactFeel)
    }
