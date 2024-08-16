package com.wiyb.server.storage.database.entity.auth.constant

import com.wiyb.server.storage.database.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.database.converter.CodedEnum
import jakarta.persistence.Converter

enum class SocialProvider(
    private val code: String
) : CodedEnum<String> {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    companion object {
        fun fromCode(code: String?): SocialProvider =
            entries.find { it.code.lowercase() == code?.lowercase() } ?: throw IllegalArgumentException("Unknown code: $code")
    }

    override fun getCode(): String = code

    @Converter(autoApply = true)
    class SocialTypeConverter : AbstractCodedEnumConverter<SocialProvider, String>(SocialProvider::class.java)
}
