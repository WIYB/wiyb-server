package com.wiyb.server.core.facade

import com.wiyb.server.core.domain.community.CommentIdPath
import com.wiyb.server.core.domain.community.CreateCommentParam
import com.wiyb.server.core.domain.community.CreatePostParam
import com.wiyb.server.core.domain.community.PaginationQuery
import com.wiyb.server.core.domain.community.PostDetailDto
import com.wiyb.server.core.domain.community.UpdateCommentParam
import com.wiyb.server.core.domain.community.UpdatePostParam
import com.wiyb.server.core.service.CommunityService
import com.wiyb.server.core.service.UserService
import com.wiyb.server.storage.database.entity.common.dto.PaginationResultDto
import com.wiyb.server.storage.database.entity.community.dto.CommentDto
import com.wiyb.server.storage.database.entity.community.dto.PostDto
import jakarta.transaction.Transactional
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class CommunityFacade(
    private val userService: UserService,
    private val communityService: CommunityService
) {
    fun getPostDetail(id: Long): PostDetailDto {
        val post = communityService.getPostDetail(id)

        communityService.increaseViewCount(id)

        return PostDetailDto.from(post)
    }

    fun getPostPage(dto: PaginationQuery): PaginationResultDto<PostDto> = communityService.getPostPage(dto.convert())

    fun createPost(param: CreatePostParam): PostDto {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val userProfile = userService.findUserProfileBySessionId(sessionId)
        val post = param.to(userProfile)

        communityService.savePost(post)

        return PostDto.from(post, userProfile)
    }

    fun updatePost(
        id: Long,
        param: UpdatePostParam
    ): PostDto {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val userProfile = userService.findUserProfileBySessionId(sessionId)
        val post = communityService.getPost(id)

        communityService.savePost(post)

        return PostDto.from(post, userProfile)
    }

    fun deletePost(id: Long): Unit = communityService.deletePost(id)

    @Transactional
    fun createComment(
        postId: Long,
        param: CreateCommentParam
    ): CommentDto {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val userProfile = userService.findUserProfileBySessionId(sessionId)
        val post = communityService.getPost(postId)
        val comment = param.to(userProfile, post)

        communityService.saveComment(comment)
        communityService.increaseCommentCount(postId)

        return CommentDto.from(comment, userProfile)
    }

    fun updateComment(
        path: CommentIdPath,
        param: UpdateCommentParam
    ): CommentDto {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val userProfile = userService.findUserProfileBySessionId(sessionId)
        val comment = communityService.getComment(path)

        communityService.saveComment(comment)

        return CommentDto.from(comment, userProfile)
    }

    @Transactional
    fun deleteComment(path: CommentIdPath) {
        communityService.decreaseCommentCount(path.postId)
        communityService.deleteComment(path.commentId)
    }
}
