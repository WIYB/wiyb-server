package com.wiyb.server.storage.database.entity.golf.dto

data class SearchParameterDto(
    val filters: SearchFilterDto,
    val page: SearchPaginationDto
) {
    companion object {
        // todo: domain 모듈 분리하면 SearchQueryDto로 parameter 수정
        fun fromQuery(
            keyword: String?,
            filters: String?,
            sessionId: String? = null,
            page: Int? = null,
            size: Int? = null,
            sort: String?
        ): SearchParameterDto {
            val keywordList = keyword?.trim()?.split("\\s+".toRegex()) ?: emptyList()
            val filterList = (filters?.split(",") ?: emptyList()).toMutableList()

            return SearchParameterDto(
                filters = SearchFilterDto.fromFilter(keywordList, filterList),
                page = SearchPaginationDto.fromQuery(sessionId, page, size, sort)
            )
        }
    }
}
