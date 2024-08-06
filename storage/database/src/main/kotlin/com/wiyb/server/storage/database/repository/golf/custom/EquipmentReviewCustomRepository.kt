package com.wiyb.server.storage.database.repository.golf.custom

import com.wiyb.server.storage.database.entity.golf.dto.EquipmentReviewDto

interface EquipmentReviewCustomRepository {
    fun findByEquipmentId(id: Long): List<EquipmentReviewDto>

    fun findSimpleByEquipmentId(id: Long): List<EquipmentReviewDto>
}
