package com.wiyb.server.core.domain.community

import com.wiyb.server.storage.database.entity.community.dto.UpdateCommentDto
import org.hibernate.validator.constraints.Length

data class UpdateCommentParam(
    @Length(max = 500)
    val content: String?
) {
    fun convert(): UpdateCommentDto =
        UpdateCommentDto(
            content = content
        )
}
