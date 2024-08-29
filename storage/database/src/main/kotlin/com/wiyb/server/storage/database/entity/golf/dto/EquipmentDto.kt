package com.wiyb.server.storage.database.entity.golf.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.detail.mapper.EquipmentDetailMapper
import com.wiyb.server.storage.database.entity.golf.dto.metric.BaseMetric

data class EquipmentDto
    @QueryProjection
    constructor(
        val id: String,
        val brand: String,
        val type: EquipmentType,
        val name: String,
        val reviewCount: Long,
        private val equipmentDetail: BaseEntity,
        val releasedYear: String?,
        val imageUrls: List<String>?,
        val viewCount: Long?,
        private val evaluationMetricTotal: List<Float>?
    ) {
        val evaluationMetricAverage: BaseMetric? =
            evaluationMetricTotal
                ?.let {
                    if (reviewCount == 0L) {
                        BaseMetric.listPad()
                    } else {
                        evaluationMetricTotal.map { elem ->
                            elem / reviewCount
                        }
                    }
                }?.let { BaseMetric.expand(type, it) }

        val detail = EquipmentDetailMapper.invoke(equipmentDetail)
        var reviews: List<EquipmentReviewDto>? = null
    }
