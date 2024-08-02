package com.wiyb.server.storage.entity.golf.constant

import com.wiyb.server.storage.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.converter.CodedEnum
import jakarta.persistence.Converter

enum class Difficulty(
    private val code: String
) : CodedEnum<String> {
    BEGINNER("beginner"),
    INTERMEDIATE("intermediate"),
    ADVANCED("advanced");

    override fun getCode(): String = code

    @Converter(autoApply = true)
    class DifficultyConverter : AbstractCodedEnumConverter<Difficulty, String>(Difficulty::class.java)
}
