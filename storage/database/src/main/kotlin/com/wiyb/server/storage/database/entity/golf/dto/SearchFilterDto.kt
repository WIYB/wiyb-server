package com.wiyb.server.storage.database.entity.golf.dto

import com.wiyb.server.storage.database.entity.golf.constant.BrandName
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType

data class SearchFilterDto(
    val brandNames: List<BrandName> = emptyList(),
    val equipmentTypes: List<EquipmentType> = emptyList()
) {
    companion object {
        fun fromFilter(filters: List<String>): SearchFilterDto {
            println(filters)
            when (filters.size) {
                0 -> return SearchFilterDto()

                else -> {
                    val brandNames = mutableListOf<BrandName>()
                    val equipmentTypes = mutableListOf<EquipmentType>()

                    filters.forEach {
                        BrandName.find(it)?.let { name -> brandNames.add(name) }
                            ?: EquipmentType.find(it)?.let { type -> equipmentTypes.add(type) }
                    }

                    return SearchFilterDto(brandNames, equipmentTypes)
                }
            }
        }
    }
}
