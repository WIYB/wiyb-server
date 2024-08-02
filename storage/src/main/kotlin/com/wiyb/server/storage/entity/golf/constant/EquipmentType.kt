package com.wiyb.server.storage.entity.golf.constant

import com.wiyb.server.storage.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.converter.CodedEnum
import jakarta.persistence.Converter

enum class EquipmentType(
    private val code: String
) : CodedEnum<String> {
    DRIVER("driver"),
    WOOD("wood"),
    HYBRID("hybrid"),
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
