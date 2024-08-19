package com.wiyb.server.storage.database.entity.golf.dto

import com.wiyb.server.storage.database.entity.common.tsidFactory
import com.wiyb.server.storage.database.entity.golf.constant.SearchSortedBy
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

data class SearchPaginationDto(
    val sessionId: String,
    val page: Int,
    val size: Int,
    private val sort: SearchSortedBy,
    var isEOF: Boolean = false
) {
    companion object {
        fun fromQuery(
            sessionId: String?,
            page: Int?,
            size: Int?,
            sort: String? = null
        ): SearchPaginationDto =
            SearchPaginationDto(
                sessionId = sessionId ?: tsidFactory.generate().toString(),
                page = sessionId?.let { page ?: 1 } ?: 1,
                size = size ?: 20,
                sort = sort?.let { SearchSortedBy.fromCode(it) } ?: SearchSortedBy.REVIEW_COUNT_DESC
            )
    }

    fun getSortedBy(): SearchSortedBy = sort

    fun setEOF() {
        isEOF = true
    }

    fun of(sort: Sort): PageRequest = PageRequest.of(page - 1, size, sort)

    fun of(): PageRequest = PageRequest.of(page - 1, size)
}
