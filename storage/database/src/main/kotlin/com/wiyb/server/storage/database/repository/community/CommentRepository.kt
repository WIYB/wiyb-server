package com.wiyb.server.storage.database.repository.community

import com.wiyb.server.storage.database.entity.community.Comment
import com.wiyb.server.storage.database.repository.community.custom.CommentCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CommentRepository :
    JpaRepository<Comment, Long>,
    CommentCustomRepository {
    @Query("SELECT COUNT(c.id) FROM comments c WHERE c.id = :commentId AND c.post.id = :postId")
    fun countByIdAndPostIdWithDeleted(
        commentId: Long,
        postId: Long
    ): Long

    @Query("SELECT COUNT(c.id) FROM comments c WHERE c.id = :commentId AND c.author.id = :authorId AND c.deletedAt IS NULL")
    fun countByIdAndAuthorId(
        commentId: Long,
        authorId: Long
    ): Long

    fun findByIdAndPostIdAndDeletedAtNull(
        id: Long,
        postId: Long
    ): Optional<Comment>
}
