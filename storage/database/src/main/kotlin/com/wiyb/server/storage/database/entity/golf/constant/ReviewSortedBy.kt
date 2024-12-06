package com.wiyb.server.storage.database.entity.golf.constant

import com.wiyb.server.storage.database.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.database.converter.CodedEnum
import jakarta.persistence.Converter

enum class ReviewSortedBy(
    private val code: String
) : CodedEnum<String> {
    CREATED_ASC("createdAsc"),
    CREATED_DESC("createdDesc"),
    LIKE_COUNT_DESC("likeCountDesc");

    companion object {
        fun fromCode(code: String): ReviewSortedBy =
            entries.find { it.code == code } ?: throw IllegalArgumentException("Unknown code: $code")
    }

    override fun getCode(): String = code

    @Converter(autoApply = true)
    class ReviewSortedByConverter : AbstractCodedEnumConverter<ReviewSortedBy, String>(ReviewSortedBy::class.java)
}
