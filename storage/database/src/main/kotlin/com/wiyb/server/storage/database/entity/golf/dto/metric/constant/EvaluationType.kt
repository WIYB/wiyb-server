package com.wiyb.server.storage.database.entity.golf.dto.metric.constant

import com.wiyb.server.storage.database.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.database.converter.CodedEnum
import jakarta.persistence.Converter

enum class EvaluationType(
    private val code: String,
    private val codeKo: String
) : CodedEnum<String> {
    FORGIVENESS("forgiveness", "관용성"),
    DISTANCE("distance", "비거리"),
    ACCURACY("accuracy", "정확도"),
    IMPACT_FEEL("impactFeel", "타감"),
    IMPACT_SOUND("impactSound", "타구음"),
    BACKSPIN("backspin", "백스핀"),
    DISTANCE_CONTROL("distanceControl", "거리감"),
    STIFFNESS("stiffness", "강성"),
    WEIGHT("weight", "무게"),
    TRAJECTORY("trajectory", "탄도"),
    TOUCH("touch", "터치감"),
    GRIP_COMFORT("gripComfort", "그립감"),
    DURABILITY("durability", "내구성");

    companion object {
        fun fromCode(code: String?): EvaluationType =
            entries.find { it.code.lowercase() == code?.lowercase() }
                ?: throw IllegalArgumentException("Unknown code: $code")
    }

    override fun getCode(): String = code

    fun getCodeKo(): String = codeKo

    @Converter(autoApply = true)
    class EvaluationTypeConverter : AbstractCodedEnumConverter<EvaluationType, String>(EvaluationType::class.java)
}
