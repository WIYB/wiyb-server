package com.wiyb.server.core.domain.community

import com.wiyb.server.core.config.annotation.ValueOfEnum
import com.wiyb.server.storage.database.entity.community.constant.Category
import com.wiyb.server.storage.database.entity.community.dto.PostPaginationDto
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive
import org.hibernate.validator.constraints.Length

data class PaginationQuery(
    @field:Positive
    @field:Length(min = 2, max = 50)
    val contextId: String? = null,
    @field:ValueOfEnum(enumClass = Category::class)
    val category: String = Category.ALL.getCode(),
    @field:Min(1)
    val offset: Int = 1,
    @field:Min(20)
    val size: Int = 20
) {
    fun convert(): PostPaginationDto = PostPaginationDto(contextId, Category.find(category)!!, offset, size)
}
