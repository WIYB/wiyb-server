package com.wiyb.server.storage.entity.constant.golf

import com.wiyb.server.storage.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.converter.CodedEnum
import jakarta.persistence.Converter

enum class Grip(
    private val code: String
) : CodedEnum<String> {
    ROUND("round"),
    RIP("rip");

    override fun getCode(): String = code

    @Converter(autoApply = true)
    class DifficultyConverter : AbstractCodedEnumConverter<Difficulty, String>(Difficulty::class.java)
}
