package com.wiyb.server.core.domain.community

import com.wiyb.server.core.config.annotation.NotAllNull
import com.wiyb.server.storage.database.entity.community.dto.UpdatePostDto
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Length

@NotAllNull
data class UpdatePostParam(
    @field:Length(max = 100)
    val title: String?,
    @field:Length(max = 2000)
    val content: String?,
    @field:Size(max = 5)
    val imageUrls: List<String>?
) {
    fun convert(): UpdatePostDto =
        UpdatePostDto(
            title = title,
            content = content,
            imageUrls = imageUrls
        )
}
