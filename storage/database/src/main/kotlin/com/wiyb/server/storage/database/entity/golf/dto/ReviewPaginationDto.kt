package com.wiyb.server.storage.database.entity.golf.dto

import com.wiyb.server.storage.database.entity.common.tsidFactory
import com.wiyb.server.storage.database.entity.golf.constant.ReviewSortedBy
import org.springframework.data.domain.PageRequest

data class ReviewPaginationDto(
    val equipmentId: Long,
    private val unsafeContextId: String?,
    val offset: Int,
    val size: Int,
    val sortedBy: ReviewSortedBy
) {
    val contextId: String = unsafeContextId ?: tsidFactory.generate().toLong().toString()

    fun of(): PageRequest = PageRequest.of(offset - 1, size)
}
