package com.wiyb.server.storage.database.entity.golf.dto

import com.wiyb.server.storage.database.entity.common.tsidFactory
import com.wiyb.server.storage.database.entity.golf.constant.SearchSortedBy
import org.springframework.data.domain.PageRequest

data class SearchPaginationDto(
    val contextId: String,
    val offset: Int,
    val size: Int,
    val sortedBy: SearchSortedBy,
    var isEmpty: Boolean = false,
    var isLast: Boolean = false
) {
    companion object {
        fun fromQuery(
            contextId: String?,
            offset: Int,
            size: Int,
            sortedBy: SearchSortedBy
        ): SearchPaginationDto =
            SearchPaginationDto(
                contextId = contextId ?: tsidFactory.generate().toLong().toString(),
                offset = contextId?.let { offset } ?: 1,
                size = size,
                sortedBy = sortedBy
            )
    }

    fun setEmpty() {
        isEmpty = true
    }

    fun setLast() {
        isLast = true
    }

    fun of(): PageRequest = PageRequest.of(offset - 1, size)
}
