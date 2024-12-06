package com.wiyb.server.core.domain.product

import com.wiyb.server.core.config.annotation.ValueOfEnum
import com.wiyb.server.storage.database.entity.golf.constant.ReviewSortedBy
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive
import org.hibernate.validator.constraints.Length

data class ReviewPaginationQuery(
    @field:Positive
    @field:Length(min = 2, max = 50)
    val contextId: String? = null,
    @field:ValueOfEnum(enumClass = ReviewSortedBy::class)
    val sort: String = ReviewSortedBy.CREATED_DESC.getCode(),
    @field:Min(1)
    val offset: Int = 1,
    @field:Min(20)
    val size: Int = 20
)
