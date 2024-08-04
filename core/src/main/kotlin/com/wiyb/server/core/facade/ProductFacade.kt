package com.wiyb.server.core.facade

import com.wiyb.server.core.service.EquipmentService
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDetailDto
import org.springframework.stereotype.Component

@Component
class ProductFacade(
    private val equipmentService: EquipmentService
) {
    fun getProductDetail(productId: Long): EquipmentDetailDto {
        val equipmentDetailDto = equipmentService.findOneWithDetailById(productId)
        return equipmentDetailDto
    }
}
