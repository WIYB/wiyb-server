package com.wiyb.server.storage.database.repository.golf.custom

import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDto

interface EquipmentCustomRepository {
    fun findSimpleById(id: Long): EquipmentSimpleDto?

    fun findReviewCounts(id: List<Long>): List<Long>

    fun findByIdList(idList: List<Long>): List<EquipmentSimpleDto>

    fun findBySearchParameters(parameter: SearchParameterDto): List<EquipmentSimpleDto>

    fun findMostViewedProduct(
        type: EquipmentType? = null,
        limit: Long? = null
    ): List<EquipmentSimpleDto>
}
