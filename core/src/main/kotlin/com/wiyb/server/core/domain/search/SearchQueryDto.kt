package com.wiyb.server.core.domain.search

import com.wiyb.server.core.config.annotation.SearchFilterKeywords
import com.wiyb.server.core.config.annotation.ValueOfEnum
import com.wiyb.server.storage.database.entity.golf.constant.SearchSortedBy
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class SearchQueryDto(
    @field:Min(2)
    @field:NotNull
    val keyword: String,
    @field:SearchFilterKeywords
    val filters: String?,
    @field:ValueOfEnum(enumClass = SearchSortedBy::class)
    val sort: String?
)
