package com.wiyb.server.storage.database.entity.community

import com.wiyb.server.storage.database.converter.StringListConverter
import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.community.constant.Category
import com.wiyb.server.storage.database.entity.community.dto.UpdatePostDto
import com.wiyb.server.storage.database.entity.user.UserProfile
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "posts")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE posts SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class Post(
    userProfile: UserProfile,
    category: Category,
    title: String,
    content: String,
    imageUrls: List<String>?
) : BaseEntity() {
    @Column(name = "category", nullable = false)
    var category: Category = category
        protected set

    @Column(name = "title", nullable = false)
    var title: String = title
        protected set

    @Column(name = "content", columnDefinition = "text", nullable = false)
    var content: String = content
        protected set

    @ColumnDefault("0")
    @Column(name = "view_count", nullable = false)
    var viewCount: Long = 0
        protected set

    @ColumnDefault("0")
    @Column(name = "comment_count", nullable = false)
    var commentCount: Long = 0
        protected set

    @Convert(converter = StringListConverter::class)
    @Column(name = "image_urls")
    var imageUrls: List<String>? = imageUrls
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = [CascadeType.REMOVE])
    protected val mutableComments: MutableList<Comment> = mutableListOf()
    val comments: List<Comment> get() = mutableComments.toList()

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    var author: UserProfile = userProfile
        protected set

    fun update(dto: UpdatePostDto) {
        dto.title?.let { title = it }
        dto.content?.let { content = it }
        dto.imageUrls?.let { imageUrls = it }
    }
}
