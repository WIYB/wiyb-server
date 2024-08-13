package com.wiyb.server.core.domain.search

import com.wiyb.server.core.config.annotation.SearchFilterKeywords
import com.wiyb.server.core.config.annotation.ValueOfEnum
import com.wiyb.server.storage.database.entity.golf.constant.SearchSortedBy

data class SearchQueryDtoV2(
    val keyword: String?,
    @field:SearchFilterKeywords
    val filters: String?,
    @field:ValueOfEnum(enumClass = SearchSortedBy::class)
    val sort: String?
)
