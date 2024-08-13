package com.wiyb.server.storage.database.entity.golf.constant

import com.wiyb.server.storage.database.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.database.converter.CodedEnum
import jakarta.persistence.Converter

enum class EquipmentType(
    private val code: String,
    private val codeKo: String
) : CodedEnum<String> {
    DRIVER("driver", "드라이버"),
    WOOD("wood", "우드"),
    HYBRID("hybrid", "유틸/하이브리드"),
    IRON("iron", "아이언"),
    WEDGE("wedge", "웨지/웻지"),
    PUTTER("putter", "퍼터"),
    SHAFT("shaft", "샤프트"),
    GRIP("grip", "그립"),
    BALL("ball", "볼/공"),
    OTHER("other", "기타");

    companion object {
        fun find(code: String): EquipmentType? = entries.find { it.code.lowercase() == code.lowercase() }

        fun findContains(code: String): EquipmentType? = entries.find { it.code.lowercase().contains(code.lowercase()) }

        fun findContainsKo(code: String): EquipmentType? = entries.find { it.codeKo.contains(code) }
    }

    override fun getCode(): String = code

    @Converter(autoApply = true)
    class EquipmentTypeConverter : AbstractCodedEnumConverter<EquipmentType, String>(EquipmentType::class.java)
}
