package com.wiyb.server.core.facade

import com.wiyb.server.core.service.EquipmentService
import org.springframework.stereotype.Component

@Component
class SearchFacade(
    private val equipmentService: EquipmentService
) {
    fun integrateSearch(keyword: String) = equipmentService.findByNameKeyword(keyword).sortedBy { it.reviewCount.dec() }
}
