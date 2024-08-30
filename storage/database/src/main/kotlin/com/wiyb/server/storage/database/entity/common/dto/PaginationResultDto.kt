package com.wiyb.server.storage.database.entity.common.dto

import org.springframework.data.domain.Page

data class PaginationResultDto<T>(
    val metadata: PaginationMetadataDto,
    val content: List<T>
) {
    companion object {
        fun <K> fromPage(
            contextId: String,
            page: Page<K>
        ): PaginationResultDto<K> =
            PaginationResultDto(
                metadata =
                    PaginationMetadataDto(
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
