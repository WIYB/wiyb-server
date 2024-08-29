package com.wiyb.server.core.facade

import com.wiyb.server.core.service.EquipmentService
import com.wiyb.server.core.service.SearchService
import com.wiyb.server.storage.database.entity.common.dto.SearchResultDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDto
import org.springframework.stereotype.Component

@Component
class SearchFacade(
    private val equipmentService: EquipmentService,
    private val searchService: SearchService
) {
    fun productIntegrateSearch(dto: SearchParameterDto): SearchResultDto<EquipmentSimpleDto> = equipmentService.findBySearchParameters(dto)

    fun getPopularKeywords() = searchService.getPopularKeywords()
}
