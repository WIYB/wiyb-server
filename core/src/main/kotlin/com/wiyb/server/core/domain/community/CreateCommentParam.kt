package com.wiyb.server.core.domain.community

import com.wiyb.server.storage.database.entity.community.Comment
import com.wiyb.server.storage.database.entity.community.Post
import com.wiyb.server.storage.database.entity.user.UserProfile
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.hibernate.validator.constraints.Length

data class CreateCommentParam(
    @field:NotBlank
    @Length(max = 500)
    val content: String,
    @field:Positive
    val replyTo: Long? = null
) {
    fun to(
        userProfile: UserProfile,
        post: Post
    ): Comment =
        Comment(
            userProfile = userProfile,
            post = post,
            replyTo = replyTo,
            content = content
        )
}
