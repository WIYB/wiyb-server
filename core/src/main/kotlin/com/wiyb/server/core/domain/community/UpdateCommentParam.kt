package com.wiyb.server.core.domain.community

import org.hibernate.validator.constraints.Length

data class UpdateCommentParam(
    @Length(max = 500)
    val content: String?
)
