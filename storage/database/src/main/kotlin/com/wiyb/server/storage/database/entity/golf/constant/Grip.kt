package com.wiyb.server.storage.database.entity.golf.constant

import com.wiyb.server.storage.database.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.database.converter.CodedEnum
import jakarta.persistence.Converter

enum class Grip(
    private val code: String
) : CodedEnum<String> {
    ROUND("round"),
    RIP("rip");

    override fun getCode(): String = code

    @Converter(autoApply = true)
    class GripConverter : AbstractCodedEnumConverter<Grip, String>(Grip::class.java)
}
