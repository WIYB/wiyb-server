package com.wiyb.server.storage.database.entity.golf.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.database.entity.golf.Brand
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.EquipmentDetail
import com.wiyb.server.storage.database.entity.golf.EquipmentReview

data class EquipmentDetailDto
    @QueryProjection
    constructor(
        private val brand: Brand,
        private val equipment: Equipment,
        private val equipmentDetail: EquipmentDetail,
        private val equipmentReviews: List<EquipmentReview>
    ) {
        // TODO("Implement this")
    }
