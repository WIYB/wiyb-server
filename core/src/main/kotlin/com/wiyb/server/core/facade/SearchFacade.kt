package com.wiyb.server.core.facade

import com.wiyb.server.core.domain.product.IntegrateSearchDto
import com.wiyb.server.core.service.EquipmentService
import com.wiyb.server.core.service.UserService
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDto
import com.wiyb.server.storage.database.entity.user.dto.UserSimpleProfileDto
import org.springframework.stereotype.Component

@Component
class SearchFacade(
    private val userService: UserService,
    private val equipmentService: EquipmentService
) {
    fun integrateSearch(dto: SearchParameterDto): IntegrateSearchDto {
        val users = emptyList<UserSimpleProfileDto>()
        val equipments = equipmentService.findBySearchParameters(dto)

        return IntegrateSearchDto(users, equipments)
    }
}
