package com.wiyb.server.storage.database.entity.golf.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.database.entity.golf.EquipmentDetail
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType

data class EquipmentDto
    @QueryProjection
    constructor(
        val id: String,
        val brand: String,
        val type: EquipmentType,
        val name: String,
        val releasedYear: String?,
        val viewCount: Long,
        val evaluatedCount: Long,
        private val evaluationMetricTotal: List<Float>,
        val imageUrls: List<String>?,
        private val equipmentDetail: EquipmentDetail,
        val reviewCount: Long
    ) {
        val evaluationMetricAverage: List<Float> =
            evaluatedCount.let {
                if (it == 0L) {
                    listOf(0f, 0f, 0f, 0f, 0f, 0f)
                } else {
                    evaluationMetricTotal.map { elem ->
                        elem / evaluatedCount
                    }
                }
            }
        val detail: EquipmentDetailDto = EquipmentDetailDto(equipmentDetail)
        var reviews: List<EquipmentReviewDto> = emptyList()
    }
