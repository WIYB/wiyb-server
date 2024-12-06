package com.wiyb.server.core.domain.community

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class CommentIdPath(
    @field:NotNull
    @field:Positive
    val postId: Long,
    @field:NotNull
    @field:Positive
    val commentId: Long
)
