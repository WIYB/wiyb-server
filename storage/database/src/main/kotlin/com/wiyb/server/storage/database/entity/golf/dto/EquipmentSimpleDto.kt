package com.wiyb.server.storage.database.entity.golf.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.dto.metric.BaseMetric

data class EquipmentSimpleDto
    @QueryProjection
    constructor(
        val id: String,
        val brand: String,
        val type: EquipmentType,
        val name: String,
        val viewCount: Long,
        val reviewCount: Long,
        private val evaluationMetricTotal: List<Float>? = null,
        val releasedYear: String?,
        val imageUrls: List<String>?
    ) {
        val score: Float? = evaluationMetricTotal?.let { BaseMetric.average(type, reviewCount, evaluationMetricTotal) }
    }
