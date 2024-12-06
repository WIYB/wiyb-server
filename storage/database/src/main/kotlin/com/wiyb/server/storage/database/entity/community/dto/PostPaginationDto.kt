package com.wiyb.server.storage.database.entity.community.dto

import com.wiyb.server.storage.database.entity.common.tsidFactory
import com.wiyb.server.storage.database.entity.community.constant.Category
import org.springframework.data.domain.PageRequest

data class PostPaginationDto(
    private val unsafeContextId: String?,
    val category: Category,
    val offset: Int,
    val size: Int
) {
    val contextId: String = unsafeContextId ?: tsidFactory.generate().toLong().toString()

    fun of(): PageRequest = PageRequest.of(offset - 1, size)
}
