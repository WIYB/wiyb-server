package com.wiyb.server.core.domain.product.mapper

import com.wiyb.server.core.domain.product.ReviewPaginationQuery
import com.wiyb.server.storage.database.entity.golf.constant.ReviewSortedBy
import com.wiyb.server.storage.database.entity.golf.dto.ReviewPaginationDto

class ReviewPaginationMapper {
    companion object {
        fun to(
            equipmentId: Long,
            query: ReviewPaginationQuery
        ): ReviewPaginationDto =
            ReviewPaginationDto(
                equipmentId = equipmentId,
                unsafeContextId = query.contextId,
                offset = query.contextId?.let { query.offset } ?: 1,
                size = query.size,
                sortedBy = ReviewSortedBy.fromCode(query.sort)
            )
    }
}
