package com.wiyb.server.storage.database.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class FloatListConverter : AttributeConverter<List<Float>, String> {
    private val separator = ","

    override fun convertToDatabaseColumn(attribute: List<Float>?): String? = attribute?.joinToString(separator)

    override fun convertToEntityAttribute(data: String?): List<Float> =
        data?.split(separator)?.mapNotNull { it.toFloatOrNull() } ?: emptyList()
}
