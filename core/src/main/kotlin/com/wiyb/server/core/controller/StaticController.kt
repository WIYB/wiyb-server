package com.wiyb.server.core.controller

import com.wiyb.server.core.facade.ProductFacade
import com.wiyb.server.storage.database.entity.golf.dto.BrandDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentTypeDto
import com.wiyb.server.storage.database.entity.golf.dto.metric.EvaluationTypeDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/static")
@RestController
class StaticController(
    private val productFacade: ProductFacade
) {
    @GetMapping("/brand")
    fun brandConstantList(): List<BrandDto> = productFacade.findBrandList()

    @GetMapping("/equipment/type")
    fun equipmentConstantList(): List<EquipmentTypeDto> = EquipmentTypeDto.toDtoList()

    @GetMapping("/equipment/metric")
    fun equipmentMetricList(): List<EvaluationTypeDto> = EvaluationTypeDto.toDtoList()
}
