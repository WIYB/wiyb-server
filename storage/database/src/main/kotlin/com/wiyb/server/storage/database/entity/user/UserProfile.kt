package com.wiyb.server.storage.database.entity.user

import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.user.constant.Gender
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDate

@Entity(name = "user_profiles")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE user_profiles SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class UserProfile(
    user: User,
    nickname: String,
    gender: Gender,
    birth: LocalDate,
    handy: Int? = null,
    height: Int? = null,
    weight: Int? = null,
    imageUrl: String? = null
) : BaseEntity(user.id) {
    @Column(name = "nickname", nullable = false)
    var nickname: String = nickname
        protected set

    @Column(name = "gender", nullable = false)
    var gender: Gender = gender
        protected set

    @Column(name = "birth", columnDefinition = "date", nullable = false)
    var birth: LocalDate = birth
        protected set

    @Column(name = "handy")
    var handy: Int? = handy
        protected set

    @Column(name = "height")
    var height: Int? = height
        protected set

    @Column(name = "weight")
    var weight: Int? = weight
        protected set

    @Column(name = "image_url")
    var imageUrl: String? = imageUrl
        protected set

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    var user: User = user
        protected set

    fun update(
        nickname: String? = null,
        gender: Gender? = null,
        birth: LocalDate? = null,
        handy: Int? = null,
        height: Int? = null,
        weight: Int? = null,
        imageUrl: String? = null
    ) {
        nickname?.let { this.nickname = it }
        gender?.let { this.gender = it }
        birth?.let { this.birth = it }
        handy?.let { this.handy = it }
        height?.let { this.height = it }
        weight?.let { this.weight = it }
        imageUrl?.let { this.imageUrl = it }
    }
}
