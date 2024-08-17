package com.wiyb.server.storage.database.entity.golf.dto

import com.wiyb.server.storage.database.entity.golf.constant.SearchSortedBy

data class SearchParameterDto(
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
            val keywordList = keyword?.trim()?.split("\\s+".toRegex()) ?: emptyList()
            val filterList = (filters?.split(",") ?: emptyList()).toMutableList()

            return SearchParameterDto(
                filters = SearchFilterDto.fromFilter(keywordList, filterList),
                sortedBy = sort?.let { SearchSortedBy.fromCode(it) } ?: SearchSortedBy.REVIEW_COUNT_DESC
            )
        }
    }
}
