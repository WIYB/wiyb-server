package com.wiyb.server.storage.database.entity.golf.dto

import com.wiyb.server.storage.database.entity.golf.constant.BrandName
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType

data class SearchFilterDto(
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
        ): SearchFilterDto {
            when (keywords.size + filters.size) {
                0 -> return SearchFilterDto()

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
                            ?: EquipmentType.find(it)?.let { type ->
                                isContainedEquipmentType = true
                                equipmentTypes.add(type)
                            } ?: EquipmentType.findKo(it)?.let { type ->
                            isContainedEquipmentType = true
                            equipmentTypes.add(type)
                        } ?: keywordList.add(it)
                    }

                    return SearchFilterDto(
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
