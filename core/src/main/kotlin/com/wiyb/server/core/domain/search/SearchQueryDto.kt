package com.wiyb.server.core.domain.search

import com.wiyb.server.core.config.annotation.ValueOfEnum
import com.wiyb.server.storage.database.entity.golf.constant.SearchSortedBy
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class SearchQueryDto(
    @field:Length(min = 2, max = 50, message = "keyword length must be between 2 and 50")
    @field:NotBlank
    val keyword: String,
//    @field:SearchFilterKeywords
    val filters: String = "",
    @field:ValueOfEnum(enumClass = SearchSortedBy::class)
    val sort: String = "reviewCountDesc",
    @field:Length(min = 2, max = 50)
    val contextId: String?,
    @field:Min(1)
    val offset: Int = 1,
    @field:Min(20)
    val size: Int = 20
)
