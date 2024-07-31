package com.wiyb.server.storage.entity.constant.golf

import com.wiyb.server.storage.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.converter.CodedEnum
import jakarta.persistence.Converter

enum class Head(
    private val code: String
) : CodedEnum<String> {
    DRIVER("driver"),
    WOOD("wood"),
    HYBRID("hybrid"),
    IRON("iron"),
    WEDGE("wedge"),
    PUTTER("putter");

    override fun getCode(): String = code

    @Converter(autoApply = true)
    class HeadConverter : AbstractCodedEnumConverter<Head, String>(Head::class.java)
}
