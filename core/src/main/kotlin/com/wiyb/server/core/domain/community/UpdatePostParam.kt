package com.wiyb.server.core.domain.community

import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Length

data class UpdatePostParam(
    @field:Length(max = 100)
    val title: String?,
    @field:Length(max = 2000)
    val content: String?,
    @field:Size(max = 5)
    val imageUrls: List<String>?
)
