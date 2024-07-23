package com.wiyb.server.storage.entity.constant

import com.wiyb.server.storage.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.converter.CodedEnum
import jakarta.persistence.Converter

enum class SocialType(
    private val type: String
) : CodedEnum<String> {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    override fun getCode(): String = type

    @Converter(autoApply = true)
    class SocialTypeConverter : AbstractCodedEnumConverter<SocialType, String>(SocialType::class.java)
}
