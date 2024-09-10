package com.wiyb.server.core.domain.community

import com.wiyb.server.core.config.annotation.ValueOfEnum
import com.wiyb.server.storage.database.entity.community.Post
import com.wiyb.server.storage.database.entity.community.constant.Category
import com.wiyb.server.storage.database.entity.user.UserProfile
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Length

data class CreatePostParam(
    @field:NotBlank
    @field:ValueOfEnum(enumClass = Category::class)
    val category: String,
    @field:NotBlank
    @field:Length(max = 100)
    val title: String,
    @field:NotBlank
    @field:Length(max = 2000)
    val content: String,
    @field:Size(max = 5)
    val imageUrls: List<String>?
) {
    fun to(userProfile: UserProfile): Post =
        Post(
            userProfile = userProfile,
            category = Category.find(category)!!,
            title = title,
            content = content,
            imageUrls = imageUrls
        )
}
