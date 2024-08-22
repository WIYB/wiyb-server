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
                        totalOffset = page.totalPages,
                        size = page.content.size,
                        totalSize = page.totalElements.toInt(),
                        isEmpty = page.isEmpty,
                        isLast = page.isLast
                    ),
                content = page.content
            )
    }
}
