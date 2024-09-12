package com.wiyb.server.storage.database.repository.community

import com.wiyb.server.storage.database.entity.community.Post
import com.wiyb.server.storage.database.repository.community.custom.PostCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PostRepository :
    JpaRepository<Post, Long>,
    PostCustomRepository {
    @Query("SELECT COUNT(p.id) FROM posts p WHERE p.id = :postId AND p.author.id = :authorId")
    fun countByIdAndAuthorId(
        postId: Long,
        authorId: Long
    ): Long

    @Modifying
    @Query("UPDATE posts p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    fun increaseViewCount(id: Long)

    @Modifying
    @Query("UPDATE posts p SET p.commentCount = p.commentCount + 1 WHERE p.id = :id")
    fun increaseCommentCount(id: Long)

    @Modifying
    @Query("UPDATE posts p SET p.commentCount = p.commentCount - 1 WHERE p.id = :id AND p.commentCount > 0")
    fun decreaseCommentCount(id: Long)
}
