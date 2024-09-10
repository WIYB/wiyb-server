package com.wiyb.server.core.controller

import com.wiyb.server.core.domain.community.CommentIdPath
import com.wiyb.server.core.domain.community.CreateCommentParam
import com.wiyb.server.core.domain.community.CreatePostParam
import com.wiyb.server.core.domain.community.PaginationQuery
import com.wiyb.server.core.domain.community.PostDetailDto
import com.wiyb.server.core.domain.community.PostIdPath
import com.wiyb.server.core.domain.community.UpdateCommentParam
import com.wiyb.server.core.domain.community.UpdatePostParam
import com.wiyb.server.core.facade.CommunityFacade
import com.wiyb.server.storage.database.entity.common.dto.PaginationResultDto
import com.wiyb.server.storage.database.entity.community.dto.CommentDto
import com.wiyb.server.storage.database.entity.community.dto.PostDto
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/community")
class CommunityController(
    private val communityFacade: CommunityFacade
) {
    /**
     * Post
     */

    @GetMapping("/post")
    fun getPostPage(
        @Valid query: PaginationQuery
    ): ResponseEntity<PaginationResultDto<PostDto>> = ResponseEntity.ok(communityFacade.getPostPage(query))

    @GetMapping("/post/{postId}")
    fun getPostDetail(
        @Valid path: PostIdPath
    ): ResponseEntity<PostDetailDto> = ResponseEntity.ok(communityFacade.getPostDetail(path.postId))

    @PostMapping("/post")
    fun createPost(
        @Valid @RequestBody body: CreatePostParam
    ): ResponseEntity<PostDto> = ResponseEntity.ok(communityFacade.createPost(body))

    @PatchMapping("/post/{postId}")
    fun updatePost(
        @Valid path: PostIdPath,
        @Valid @RequestBody body: UpdatePostParam
    ): ResponseEntity<PostDto> = ResponseEntity.ok(communityFacade.updatePost(path.postId, body))

    @DeleteMapping("/post/{postId}")
    fun deletePost(
        @Valid path: PostIdPath
    ): ResponseEntity<Unit> = ResponseEntity.ok(communityFacade.deletePost(path.postId))

    /**
     * Comment
     */

    @PostMapping("/post/{postId}/comment")
    fun createComment(
        @Valid path: PostIdPath,
        @Valid @RequestBody body: CreateCommentParam
    ): ResponseEntity<CommentDto> = ResponseEntity.ok(communityFacade.createComment(path.postId, body))

    @PatchMapping("/post/{postId}/comment/{commentId}")
    fun updateComment(
        @Valid path: CommentIdPath,
        @Valid @RequestBody body: UpdateCommentParam
    ): ResponseEntity<CommentDto> = ResponseEntity.ok(communityFacade.updateComment(path, body))

    @DeleteMapping("/post/{postId}/comment/{commentId}")
    fun deleteComment(
        @Valid path: CommentIdPath
    ): ResponseEntity<Unit> = ResponseEntity.ok(communityFacade.deleteComment(path))
}
