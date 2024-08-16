package com.wiyb.server.core.domain.common

import com.wiyb.server.storage.database.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.database.converter.CodedEnum
import jakarta.persistence.Converter

enum class ResponseType(
    private val code: String
) : CodedEnum<String> {
    SERVLET("servlet"),
    ENTITY("entity");

    companion object {
        fun fromCode(code: String?): ResponseType =
            entries.find { it.code.lowercase() == code?.lowercase() }
                ?: throw IllegalArgumentException("Unknown code: $code")

        fun find(code: String): ResponseType? = entries.find { it.code.lowercase() == code.lowercase() }
    }

    override fun getCode(): String = code

    @Converter(autoApply = true)
    class ResponseTypeConverter : AbstractCodedEnumConverter<ResponseType, String>(ResponseType::class.java)
}
