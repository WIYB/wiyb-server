package com.wiyb.server.core.domain.community

import com.wiyb.server.storage.database.entity.community.constant.Category
import com.wiyb.server.storage.database.entity.community.dto.PostDto
import com.wiyb.server.storage.database.entity.user.dto.UserSimpleProfileDto
import java.time.LocalDateTime

data class PostDetailDto(
    val id: String,
    val category: Category,
    val title: String,
    val content: String,
    val viewCount: Long,
    val commentCount: Long,
    val imageUrls: List<String>? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val author: UserSimpleProfileDto,
    val comments: List<CommentDetailDto>? = null
) {
    companion object {
        fun from(dto: PostDto): PostDetailDto =
            PostDetailDto(
                id = dto.id,
                category = dto.category,
                title = dto.title,
                content = dto.content!!,
                viewCount = dto.viewCount,
                commentCount = dto.commentCount,
                imageUrls = dto.imageUrls,
                createdAt = dto.createdAt,
                updatedAt = dto.updatedAt,
                author = dto.author,
                comments = dto.comments?.let { CommentDetailDto.from(it) }
            )
    }
}
