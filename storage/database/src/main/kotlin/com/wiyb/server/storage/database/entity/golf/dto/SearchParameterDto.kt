package com.wiyb.server.storage.database.entity.golf.dto

import com.wiyb.server.storage.database.entity.golf.constant.SearchSortedBy

data class SearchParameterDto(
    val keyword: String? = null,
    val filters: SearchFilterDto,
    val sortedBy: SearchSortedBy
) {
    companion object {
        // todo: domain 모듈 분리하면 SearchQueryDto로 parameter 수정
        fun fromQuery(
            keyword: String?,
            filters: String?,
            sort: String?
        ): SearchParameterDto {
            val filterList = (filters?.split(",") ?: emptyList()).toMutableList()

            return SearchParameterDto(
                keyword = keyword,
                filters = SearchFilterDto.fromFilter(filterList),
                sortedBy = sort?.let { SearchSortedBy.fromCode(it) } ?: SearchSortedBy.REVIEW_COUNT_DESC
            )
        }
    }
}
