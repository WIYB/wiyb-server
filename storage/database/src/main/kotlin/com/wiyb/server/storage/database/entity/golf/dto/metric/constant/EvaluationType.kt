package com.wiyb.server.storage.database.entity.golf.dto.metric.constant

import com.wiyb.server.storage.database.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.database.converter.CodedEnum
import jakarta.persistence.Converter

enum class EvaluationType(
    private val code: String
) : CodedEnum<String> {
    FORGIVENESS("forgiveness"),
    DISTANCE("distance"),
    ACCURACY("accuracy"),
    IMPACT_FEEL("impactFeel"),
    IMPACT_SOUND("impactSound"),
    BACKSPIN("backspin"),
    DISTANCE_CONTROL("distanceControl"),
    STIFFNESS("stiffness"),
    WEIGHT("weight"),
    TRAJECTORY("trajectory"),
    TOUCH("touch"),
    GRIP_COMFORT("gripComfort"),
    DURABILITY("durability");

    companion object {
        fun fromCode(code: String?): EvaluationType =
            entries.find { it.code.lowercase() == code?.lowercase() }
                ?: throw IllegalArgumentException("Unknown code: $code")
    }

    override fun getCode(): String = code

    @Converter(autoApply = true)
    class EvaluationTypeConverter : AbstractCodedEnumConverter<EvaluationType, String>(EvaluationType::class.java)
}
