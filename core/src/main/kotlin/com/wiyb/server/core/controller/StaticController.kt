package com.wiyb.server.core.controller

import com.wiyb.server.storage.database.entity.golf.constant.BrandName
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/static")
@RestController
class StaticController {
    @GetMapping("/brand")
    fun brandConstantList(): List<String> = BrandName.entries.map { it.getCode() }

    @GetMapping("/equipment")
    fun equipmentConstantList(): List<String> = EquipmentType.entries.map { it.getCode() }
}
