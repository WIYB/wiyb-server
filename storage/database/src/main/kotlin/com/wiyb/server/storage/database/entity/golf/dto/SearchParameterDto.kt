package com.wiyb.server.storage.database.entity.golf.dto

import com.wiyb.server.storage.database.entity.golf.constant.SearchSortedBy

data class SearchParameterDto(
    val filters: SearchFilterDto,
    val page: SearchPaginationDto
) {
    companion object {
        // todo: domain 모듈 분리하면 SearchQueryDto로 parameter 수정
        fun fromQuery(
            keyword: String,
            filters: String,
            sort: String,
            contextId: String?,
            offset: Int,
            size: Int
        ): SearchParameterDto {
            val keywordList = keyword.trim().split("\\s+".toRegex())
            val filterList = filters.split(",").toMutableList()

            return SearchParameterDto(
                filters = SearchFilterDto.fromFilter(keywordList, filterList),
                page =
                    SearchPaginationDto.fromQuery(
                        contextId = contextId,
                        offset = offset,
                        size = size,
                        sortedBy = SearchSortedBy.fromCode(sort)
                    )
            )
        }
    }
}
