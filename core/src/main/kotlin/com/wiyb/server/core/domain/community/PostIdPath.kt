package com.wiyb.server.core.domain.community

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class PostIdPath(
    @field:NotNull
    @field:Positive
    val postId: Long
)
