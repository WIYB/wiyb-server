package com.wiyb.server.storage.database.entity.golf.constant

import com.wiyb.server.storage.database.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.database.converter.CodedEnum
import jakarta.persistence.Converter

enum class EquipmentType(
    private val code: String
) : CodedEnum<String> {
    DRIVER("driver"),
    WOOD("wood"),
    UTILITY("utility"),
    IRON("iron"),
    WEDGE("wedge"),
    PUTTER("putter"),
    SHAFT("shaft"),
    GRIP("grip"),
    BALL("ball"),
    OTHER("other");

    override fun getCode(): String = code

    @Converter(autoApply = true)
    class EquipmentTypeConverter : AbstractCodedEnumConverter<EquipmentType, String>(EquipmentType::class.java)
}
