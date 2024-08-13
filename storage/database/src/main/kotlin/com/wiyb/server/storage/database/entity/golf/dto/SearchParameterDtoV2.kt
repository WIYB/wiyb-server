package com.wiyb.server.storage.database.entity.golf.dto

import com.wiyb.server.storage.database.entity.golf.constant.SearchSortedBy

data class SearchParameterDtoV2(
    val filters: SearchFilterDtoV2,
    val sortedBy: SearchSortedBy
) {
    companion object {
        // todo: domain 모듈 분리하면 SearchQueryDto로 parameter 수정
        fun fromQuery(
            keyword: String?,
            filters: String?,
            sort: String?
        ): SearchParameterDtoV2 {
            val keywordList = keyword?.trim()?.split("\\s+".toRegex()) ?: emptyList()
            val filterList = (filters?.split(",") ?: emptyList()).toMutableList()

            return SearchParameterDtoV2(
                filters = SearchFilterDtoV2.fromFilter(keywordList, filterList),
                sortedBy = sort?.let { SearchSortedBy.fromCode(it) } ?: SearchSortedBy.REVIEW_COUNT_DESC
            )
        }
    }
}
