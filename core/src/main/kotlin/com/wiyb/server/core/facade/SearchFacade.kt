package com.wiyb.server.core.facade

import com.wiyb.server.core.domain.golf.IntegrateSearchDto
import com.wiyb.server.core.service.EquipmentService
import com.wiyb.server.core.service.UserService
import org.springframework.stereotype.Component

@Component
class SearchFacade(
    private val userService: UserService,
    private val equipmentService: EquipmentService
) {
    fun integrateSearch(keyword: String): IntegrateSearchDto {
        val users = userService.findUserSimpleProfileDtoByNameKeyword(keyword)
        val equipments = equipmentService.findByNameKeyword(keyword)

        return IntegrateSearchDto(users, equipments)
    }
}
