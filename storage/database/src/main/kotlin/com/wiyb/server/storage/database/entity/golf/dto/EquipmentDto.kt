package com.wiyb.server.storage.database.entity.golf.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.detail.mapper.EquipmentDetailMapper

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
        val evaluatedCount: Long?,
        private val evaluationMetricTotal: List<Float>?
    ) {
        val evaluationMetricAverage: List<Float>? =
            evaluationMetricTotal?.let {
                if (evaluatedCount == null || evaluatedCount == 0L) {
                    listOf(0f, 0f, 0f, 0f, 0f, 0f)
                } else {
                    evaluationMetricTotal.map { elem ->
                        elem / evaluatedCount
                    }
                }
            }

        val detail = EquipmentDetailMapper.invoke(equipmentDetail)
        var reviews: List<EquipmentReviewDto>? = null
    }
