package com.wiyb.server.storage.database.repository.golf.custom

import com.wiyb.server.storage.database.entity.golf.constant.SearchSortedBy
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchFilterDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDtoV2

interface EquipmentCustomRepository {
    fun findByIdList(idList: List<Long>): List<EquipmentSimpleDto>

    fun findBySearchParameters(
        keyword: String?,
        filters: SearchFilterDto,
        sort: SearchSortedBy
    ): List<EquipmentSimpleDto>

    fun findBySearchParametersV2(parameter: SearchParameterDtoV2): List<EquipmentSimpleDto>
}
