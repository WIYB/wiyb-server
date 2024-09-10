package com.wiyb.server.storage.database.repository.community

import com.wiyb.server.storage.database.entity.community.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByIdAndPostId(
        id: Long,
        postId: Long
    ): Optional<Comment>
}
