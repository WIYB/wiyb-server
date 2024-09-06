package com.wiyb.server.storage.database.repository.golf.custom

import com.wiyb.server.storage.database.entity.common.dto.PaginationResultDto
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDto
import com.wiyb.server.storage.database.entity.golf.dto.metric.constant.EvaluationType
import java.util.Optional

interface EquipmentCustomRepository {
    fun findSimpleById(id: Long): EquipmentSimpleDto?

    fun findByIdWithMetric(id: Long): Optional<Equipment>

    fun findTopScoreByMetric(
        type: EquipmentType,
        metric: EvaluationType,
        size: Int
    ): List<EquipmentSimpleDto>

    fun findReviewCounts(id: List<Long>): List<Long>

    fun findByIdList(idList: List<Long>): List<EquipmentSimpleDto>

    fun findBySearchParameters(parameter: SearchParameterDto): PaginationResultDto<EquipmentSimpleDto>

    fun findMostViewedProduct(
        type: EquipmentType? = null,
        limit: Long? = null
    ): List<EquipmentSimpleDto>
}
