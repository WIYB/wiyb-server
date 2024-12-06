package com.wiyb.server.storage.database.repository.golf.custom

import com.wiyb.server.storage.database.entity.common.dto.PaginationResultDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentReviewDto
import com.wiyb.server.storage.database.entity.golf.dto.ReviewPaginationDto

interface EquipmentReviewCustomRepository {
    fun findWithPagination(parameter: ReviewPaginationDto): PaginationResultDto<EquipmentReviewDto>

    fun findSimpleByEquipmentId(id: Long): List<EquipmentReviewDto>
}
