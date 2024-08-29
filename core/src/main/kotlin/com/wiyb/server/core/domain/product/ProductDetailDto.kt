package com.wiyb.server.core.domain.product

import com.wiyb.server.core.domain.search.YoutubeSearchResultDto
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractEquipmentDetail
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentReviewDto
import com.wiyb.server.storage.database.entity.golf.dto.metric.BaseMetric

data class ProductDetailDto(
    val id: String,
    val brand: String,
    val type: EquipmentType,
    val name: String,
    val reviewCount: Long,
    val viewCount: Long?,
    val detail: AbstractEquipmentDetail,
    val releasedYear: String?,
    val imageUrls: List<String>?,
    val evaluationMetricAverage: BaseMetric?,
    val youtubeResults: List<YoutubeSearchResultDto>?,
    var reviews: List<EquipmentReviewDto>?,
    var isBookmarked: Boolean = false
)
