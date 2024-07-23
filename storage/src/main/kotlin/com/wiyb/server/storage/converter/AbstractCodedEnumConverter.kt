package com.wiyb.server.storage.converter

import jakarta.persistence.AttributeConverter
import java.util.Objects

abstract class AbstractCodedEnumConverter<T, E>(
    private val clazz: Class<T>
) : AttributeConverter<T, E> where T : Enum<T>, T : CodedEnum<E> {
    override fun convertToDatabaseColumn(attribute: T?): E? = attribute?.getCode()

    override fun convertToEntityAttribute(data: E?): T? {
        if (Objects.isNull(data)) return null
        return clazz.enumConstants
            .firstOrNull { it.getCode() == data }
            ?: throw IllegalArgumentException("Unknown value: $data")
    }
}
