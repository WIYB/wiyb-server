package com.wiyb.server.storage.database.repository.community.custom

import com.wiyb.server.storage.database.entity.community.dto.CommentDto

interface CommentCustomRepository {
    fun findByPostId(postId: Long): List<CommentDto>
}
