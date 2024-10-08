package com.wiyb.server.core.service

import com.wiyb.server.core.domain.community.CommentIdPath
import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.storage.database.entity.common.dto.PaginationResultDto
import com.wiyb.server.storage.database.entity.community.Comment
import com.wiyb.server.storage.database.entity.community.Post
import com.wiyb.server.storage.database.entity.community.dto.CommentDto
import com.wiyb.server.storage.database.entity.community.dto.PostDto
import com.wiyb.server.storage.database.entity.community.dto.PostPaginationDto
import com.wiyb.server.storage.database.repository.community.CommentRepository
import com.wiyb.server.storage.database.repository.community.PostRepository
import org.springframework.stereotype.Service

@Service
class CommunityService(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository
) {
    fun unsafeCheckExistsComment(
        commentId: Long,
        postId: Long
    ): Boolean = commentRepository.countByIdAndPostIdWithDeleted(commentId, postId) > 0

    fun isAuthor(
        postId: Long,
        authorId: Long
    ): Boolean = postRepository.countByIdAndAuthorId(postId, authorId) > 0

    fun isCommentAuthor(
        commentId: Long,
        authorId: Long
    ): Boolean = commentRepository.countByIdAndAuthorId(commentId, authorId) > 0

    fun getPostDetail(id: Long): PostDto = postRepository.findDetailById(id).orElseThrow { CommonException(ErrorCode.POST_NOT_FOUND) }

    fun getPost(id: Long): Post = postRepository.findById(id).orElseThrow { CommonException(ErrorCode.POST_NOT_FOUND) }

    fun getPostPage(dto: PostPaginationDto): PaginationResultDto<PostDto> = postRepository.findPage(dto)

    fun savePost(post: Post): Post = postRepository.save(post)

    fun deletePost(id: Long) = postRepository.deleteById(id)

    fun getComments(postId: Long): List<CommentDto> = commentRepository.findByPostId(postId)

    fun getComment(path: CommentIdPath): Comment =
        commentRepository.findByIdAndPostIdAndDeletedAtNull(path.commentId, path.postId).orElseThrow {
            CommonException(ErrorCode.COMMENT_NOT_FOUND)
        }

    fun saveComment(comment: Comment): Comment = commentRepository.save(comment)

    fun deleteComment(id: Long) = commentRepository.deleteById(id)

    fun increaseViewCount(postId: Long) = postRepository.increaseViewCount(postId)

    fun increaseCommentCount(postId: Long) = postRepository.increaseCommentCount(postId)

    fun decreaseCommentCount(postId: Long) = postRepository.decreaseCommentCount(postId)
}
