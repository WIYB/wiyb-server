package com.wiyb.server.core.facade

import com.wiyb.server.core.domain.community.CommentIdPath
import com.wiyb.server.core.domain.community.CreateCommentParam
import com.wiyb.server.core.domain.community.CreatePostParam
import com.wiyb.server.core.domain.community.PaginationQuery
import com.wiyb.server.core.domain.community.PostDetailDto
import com.wiyb.server.core.domain.community.UpdateCommentParam
import com.wiyb.server.core.domain.community.UpdatePostParam
import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
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
    @Transactional
    fun getPostDetail(id: Long): PostDetailDto {
        val post = communityService.getPostDetail(id)

        if (post.commentCount > 0) {
            post.comments = communityService.getComments(id)
        }

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

        if (!communityService.isAuthor(id, userProfile.id)) {
            throw CommonException(ErrorCode.INSUFFICIENT_POST_AUTHORITY)
        }

        param.convert().let { post.update(it) }
        communityService.savePost(post)

        return PostDto.from(post, userProfile)
    }

    fun deletePost(id: Long) {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val userProfile = userService.findUserProfileBySessionId(sessionId)

        if (!communityService.isAuthor(id, userProfile.id)) {
            throw CommonException(ErrorCode.INSUFFICIENT_POST_AUTHORITY)
        }

        communityService.deletePost(id)
    }

    @Transactional
    fun createComment(
        postId: Long,
        param: CreateCommentParam
    ): CommentDto {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val userProfile = userService.findUserProfileBySessionId(sessionId)
        val post = communityService.getPost(postId)
        val comment = param.to(userProfile, post)

        if (param.replyTo != null && !communityService.unsafeCheckExistsComment(param.replyTo, postId)) {
            throw CommonException(ErrorCode.COMMENT_REPLY_NOT_ALLOWED)
        }

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

        if (!communityService.isCommentAuthor(comment.id, userProfile.id)) {
            throw CommonException(ErrorCode.INSUFFICIENT_POST_AUTHORITY)
        }

        param.convert().let { comment.update(it) }
        communityService.saveComment(comment)

        return CommentDto.from(comment, userProfile)
    }

    @Transactional
    fun deleteComment(path: CommentIdPath) {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val userProfile = userService.findUserProfileBySessionId(sessionId)

        if (!communityService.isCommentAuthor(path.commentId, userProfile.id)) {
            throw CommonException(ErrorCode.INSUFFICIENT_POST_AUTHORITY)
        }

        // todo: 기획 따라서. 일단은 댓글 감소는 없는 걸로
        // communityService.decreaseCommentCount(path.postId)
        communityService.deleteComment(path.commentId)
    }
}
