package com.wiyb.server.storage.database.entity.community.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.database.entity.community.Post
import com.wiyb.server.storage.database.entity.community.constant.Category
import com.wiyb.server.storage.database.entity.user.UserProfile
import com.wiyb.server.storage.database.entity.user.dto.UserSimpleProfileDto
import java.time.LocalDateTime

data class PostDto
    @QueryProjection
    constructor(
        val id: String,
        val category: Category,
        val title: String,
        val content: String? = null,
        val viewCount: Long,
        val commentCount: Long,
        val imageUrls: List<String>? = null,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val author: UserSimpleProfileDto,
        var comments: List<CommentDto>? = null
    ) {
        companion object {
            fun from(
                entity: Post,
                userProfile: UserProfile
            ): PostDto =
                PostDto(
                    id = entity.id.toString(),
                    category = entity.category,
                    title = entity.title,
                    content = entity.content,
                    viewCount = entity.viewCount,
                    commentCount = entity.commentCount,
                    imageUrls = entity.imageUrls,
                    createdAt = entity.createdAt,
                    updatedAt = entity.updatedAt,
                    author = UserSimpleProfileDto.from(userProfile),
                    comments = null
                )
        }
    }
