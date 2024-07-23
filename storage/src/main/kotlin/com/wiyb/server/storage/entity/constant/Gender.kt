package com.wiyb.server.storage.entity.constant

import com.wiyb.server.storage.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.converter.CodedEnum
import jakarta.persistence.Converter

enum class Gender(
    private val type: String
) : CodedEnum<String> {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");

    override fun getCode(): String = type

    @Converter(autoApply = true)
    class GenderConverter : AbstractCodedEnumConverter<Gender, String>(Gender::class.java)
}
