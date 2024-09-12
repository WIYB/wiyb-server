package com.wiyb.server.storage.database.entity.community

import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.community.dto.UpdateCommentDto
import com.wiyb.server.storage.database.entity.user.UserProfile
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLDelete

/**
 * Comment Entity는 특수하게 deleted_at is not null 조건이 없음.
 */
@Entity(name = "comments")
@SQLDelete(sql = "UPDATE comments SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class Comment(
    userProfile: UserProfile,
    post: Post,
    replyTo: Long? = null,
    content: String
) : BaseEntity() {
    @Column(name = "reply_to")
    var replyTo: Long? = replyTo
        protected set

    @Column(name = "content", columnDefinition = "text", nullable = false)
    var content: String = content
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    var author: UserProfile = userProfile
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    var post: Post = post
        protected set

    fun update(dto: UpdateCommentDto) {
        dto.content?.let { content = it }
    }
}
