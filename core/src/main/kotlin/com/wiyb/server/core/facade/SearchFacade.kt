package com.wiyb.server.core.facade

import com.wiyb.server.core.domain.product.IntegrateSearchDto
import com.wiyb.server.core.service.EquipmentService
import com.wiyb.server.core.service.UserService
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDto
import org.springframework.stereotype.Component

@Component
class SearchFacade(
    private val userService: UserService,
    private val equipmentService: EquipmentService
) {
    fun integrateSearch(dto: SearchParameterDto): IntegrateSearchDto {
        val users =
            dto.filters.keywords
                .isNotEmpty()
                .let { userService.findUserSimpleProfileDtoByNameKeyword(dto.filters.keywords[0]) }

        val equipments = equipmentService.findBySearchParameters(dto)

        return IntegrateSearchDto(users, equipments)
    }
}
