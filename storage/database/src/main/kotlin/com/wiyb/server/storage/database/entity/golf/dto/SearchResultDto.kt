package com.wiyb.server.storage.database.entity.golf.dto

import org.springframework.data.domain.Page

data class SearchResultDto<T>(
    val metadata: SearchMetadataDto,
    val content: List<T>
) {
    companion object {
        fun <K> fromPage(
            contextId: String,
            page: Page<K>
        ): SearchResultDto<K> =
            SearchResultDto(
                metadata =
                    SearchMetadataDto(
                        contextId = contextId,
                        offset = page.number + 1,
                        total = page.totalPages,
                        size = page.content.size,
                        isEmpty = page.isEmpty,
                        isLast = page.isLast
                    ),
                content = page.content
            )
    }
}
