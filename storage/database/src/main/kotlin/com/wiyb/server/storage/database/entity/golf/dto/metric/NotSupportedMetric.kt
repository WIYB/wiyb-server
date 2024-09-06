package com.wiyb.server.storage.database.entity.golf.dto.metric

import com.querydsl.core.annotations.QueryProjection

class NotSupportedMetric
    @QueryProjection
    constructor(
        private val mock: Float?
    ) : BaseMetric() {
        override fun flatten(): List<Float> = listPad()
    }
