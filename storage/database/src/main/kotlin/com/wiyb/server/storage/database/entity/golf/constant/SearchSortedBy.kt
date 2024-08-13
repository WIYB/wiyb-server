package com.wiyb.server.storage.database.entity.golf.constant

import com.wiyb.server.storage.database.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.database.converter.CodedEnum
import jakarta.persistence.Converter

enum class SearchSortedBy(
    private val code: String
) : CodedEnum<String> {
    RELEASED_ASC("releasedAsc"),
    RELEASED_DESC("releasedDesc"),
    REVIEW_COUNT_DESC("reviewCountDesc"),
    VIEW_COUNT_DESC("viewCountDesc");

    companion object {
        fun fromCode(code: String): SearchSortedBy =
            entries.find { it.code == code } ?: throw IllegalArgumentException("Unknown code: $code")
    }

    override fun getCode(): String = code

    @Converter(autoApply = true)
    class SearchSortedByConverter : AbstractCodedEnumConverter<SearchSortedBy, String>(SearchSortedBy::class.java)
}
