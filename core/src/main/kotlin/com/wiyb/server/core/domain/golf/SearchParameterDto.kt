package com.wiyb.server.core.domain.golf

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class SearchParameterDto(
    @field:Min(2, message = "keyword must be at least 2 characters")
    @field:NotBlank
    val keyword: String
)
