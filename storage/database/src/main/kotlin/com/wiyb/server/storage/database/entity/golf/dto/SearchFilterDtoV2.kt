package com.wiyb.server.storage.database.entity.golf.dto

import com.wiyb.server.storage.database.entity.golf.constant.BrandName
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType

data class SearchFilterDtoV2(
    val keywords: List<String> = emptyList(),
    val brandNames: List<BrandName> = emptyList(),
    val equipmentTypes: List<EquipmentType> = emptyList(),
    val isContainedBrandName: Boolean = false,
    val isContainedEquipmentType: Boolean = false
) {
    companion object {
        fun fromFilter(
            keywords: List<String>,
            filters: List<String>
        ): SearchFilterDtoV2 {
            when (keywords.size + filters.size) {
                0 -> return SearchFilterDtoV2()

                else -> {
                    val keywordList = mutableListOf<String>()
                    val brandNames = mutableListOf<BrandName>()
                    val equipmentTypes = mutableListOf<EquipmentType>()
                    var isContainedBrandName = false
                    var isContainedEquipmentType = false

                    filters.forEach {
                        BrandName.findContains(it)?.let { name -> brandNames.add(name) }
                            ?: BrandName.findContainsKo(it)?.let { name -> brandNames.add(name) }
                            ?: EquipmentType.findContains(it)?.let { type -> equipmentTypes.add(type) }
                    }

                    keywords.forEach {
                        BrandName.findContains(it)?.let { name ->
                            isContainedBrandName = true
                            brandNames.add(name)
                        }
                            ?: BrandName.findContainsKo(it)?.let { name ->
                                isContainedBrandName = true
                                brandNames.add(name)
                            }
                            ?: EquipmentType.findContains(it)?.let { type ->
                                isContainedEquipmentType = true
                                equipmentTypes.add(type)
                            } ?: EquipmentType.findContainsKo(it)?.let { type ->
                            isContainedEquipmentType = true
                            equipmentTypes.add(type)
                        } ?: keywordList.add(it)
                    }

                    return SearchFilterDtoV2(
                        keywordList,
                        brandNames,
                        equipmentTypes,
                        isContainedBrandName,
                        isContainedEquipmentType
                    )
                }
            }
        }
    }
}
