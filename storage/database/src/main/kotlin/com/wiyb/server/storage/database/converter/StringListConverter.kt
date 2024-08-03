package com.wiyb.server.storage.database.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class StringListConverter : AttributeConverter<List<String>, String> {
    private val separator = ","

    override fun convertToDatabaseColumn(attribute: List<String>?): String? = attribute?.joinToString(separator)

    override fun convertToEntityAttribute(data: String?): List<String> = listOf(*data?.split(separator)?.toTypedArray() ?: emptyArray())
}
