package com.wiyb.server.storage.entity.auth.constant

import com.wiyb.server.storage.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.converter.CodedEnum
import jakarta.persistence.Converter

enum class SocialProvider(
    private val code: String
) : CodedEnum<String> {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    override fun getCode(): String = code

    @Converter(autoApply = true)
    class SocialTypeConverter : AbstractCodedEnumConverter<SocialProvider, String>(SocialProvider::class.java)
}
