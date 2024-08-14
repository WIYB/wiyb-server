package com.wiyb.server.core.domain.search

import com.wiyb.server.core.config.annotation.SearchFilterKeywords
import com.wiyb.server.core.config.annotation.ValueOfEnum
import com.wiyb.server.storage.database.entity.golf.constant.SearchSortedBy
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class SearchQueryDto(
    @field:Length(min = 2, max = 50, message = "keyword length must be between 2 and 50")
    @field:NotBlank
    val keyword: String,
    @field:SearchFilterKeywords
    val filters: String?,
    @field:ValueOfEnum(enumClass = SearchSortedBy::class)
    val sort: String?
)
