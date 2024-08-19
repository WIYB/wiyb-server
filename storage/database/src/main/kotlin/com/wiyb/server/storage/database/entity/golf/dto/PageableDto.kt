package com.wiyb.server.storage.database.entity.golf.dto

import org.springframework.data.domain.Page

data class PageableDto<T>(
    val pageInfo: SearchPaginationDto,
    val content: List<T>
) {
    companion object {
        fun <K> fromPage(
            sessionId: String,
            page: Page<K>
        ): PageableDto<K> {
            val dto =
                PageableDto(
                    pageInfo =
                        SearchPaginationDto.fromQuery(
                            sessionId,
                            page.pageable.pageNumber + 1,
                            page.size
                        ),
                    content = page.content
                )

            if (page.isLast) dto.pageInfo.setEOF()

            return dto
        }
    }
}
