package com.wiyb.server.storage.database.repository.community.custom.impl

import com.wiyb.server.storage.database.entity.community.Comment
import com.wiyb.server.storage.database.entity.community.QComment.comment
import com.wiyb.server.storage.database.entity.community.dto.CommentDto
import com.wiyb.server.storage.database.entity.community.dto.QCommentDto
import com.wiyb.server.storage.database.entity.user.QUserProfile.userProfile
import com.wiyb.server.storage.database.entity.user.dto.QUserSimpleProfileDto
import com.wiyb.server.storage.database.repository.community.custom.CommentCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class CommentCustomRepositoryImpl :
    QuerydslRepositorySupport(Comment::class.java),
    CommentCustomRepository {
    override fun findByPostId(postId: Long): List<CommentDto> =
        from(comment)
            .select(
                QCommentDto(
                    comment.id.stringValue(),
                    comment.replyTo.stringValue(),
                    comment.content,
                    comment.createdAt,
                    comment.updatedAt,
                    comment.deletedAt,
                    QUserSimpleProfileDto(
                        comment.author.id.stringValue(),
                        comment.author.nickname,
                        comment.author.handy,
                        comment.author.height,
                        comment.author.weight,
                        comment.author.imageUrl
                    )
                )
            ).leftJoin(comment.author, userProfile)
            .where(comment.post.id.eq(postId))
            .fetch()
}
