package com.wiyb.server.core.facade

import com.wiyb.server.core.domain.product.IntegrateSearchDto
import com.wiyb.server.core.service.EquipmentService
import com.wiyb.server.core.service.UserService
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDtoV2
import com.wiyb.server.storage.database.entity.user.dto.UserSimpleProfileDto
import org.springframework.stereotype.Component

@Component
class SearchFacade(
    private val userService: UserService,
    private val equipmentService: EquipmentService
) {
    // todo: 통합 검색 method 통합 예정
    fun integrateSearch(dto: SearchParameterDto): IntegrateSearchDto {
        val users = dto.keyword?.let { userService.findUserSimpleProfileDtoByNameKeyword(it) } ?: emptyList()
        val equipments = equipmentService.findBySearchParameters(dto)

        return IntegrateSearchDto(users, equipments)
    }

    fun integrateSearchV2(dto: SearchParameterDtoV2): IntegrateSearchDto {
        val users = listOf<UserSimpleProfileDto>()
        val equipments = equipmentService.findBySearchParametersV2(dto)

        return IntegrateSearchDto(users, equipments)
    }
    // =============================
}
