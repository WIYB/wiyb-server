package com.wiyb.server.core.facade

import com.wiyb.server.core.service.EquipmentService
import com.wiyb.server.core.service.UserService
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchResultDto
import org.springframework.stereotype.Component

@Component
class SearchFacade(
    private val userService: UserService,
    private val equipmentService: EquipmentService
) {
    fun productIntegrateSearch(dto: SearchParameterDto): SearchResultDto<EquipmentSimpleDto> = equipmentService.findBySearchParameters(dto)
}
