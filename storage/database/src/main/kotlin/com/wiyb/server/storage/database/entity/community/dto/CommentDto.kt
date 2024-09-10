package com.wiyb.server.storage.database.entity.community.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.database.entity.community.Comment
import com.wiyb.server.storage.database.entity.user.UserProfile
import com.wiyb.server.storage.database.entity.user.dto.UserSimpleProfileDto
import java.time.LocalDateTime

data class CommentDto
    @QueryProjection
    constructor(
        val id: String,
        val content: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val author: UserSimpleProfileDto,
        val replyId: String? = null
    ) {
        companion object {
            fun from(
                comment: Comment,
                userProfile: UserProfile
            ): CommentDto =
                CommentDto(
                    id = comment.id.toString(),
                    content = comment.content,
                    createdAt = comment.createdAt,
                    updatedAt = comment.updatedAt,
                    author = UserSimpleProfileDto.from(userProfile)
                )
        }
    }
