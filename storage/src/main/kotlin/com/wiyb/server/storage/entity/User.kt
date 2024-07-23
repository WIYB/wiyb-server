package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.BaseEntity
import com.wiyb.server.storage.entity.constant.Gender
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.LocalDate

@Entity(name = "users")
class User(
    name: String,
    nickname: String,
    gender: Gender,
    birth: LocalDate,
    imageUrl: String?
) : BaseEntity() {
    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "nickname", nullable = false)
    var nickname: String = nickname
        protected set

    @Column(name = "gender", nullable = false)
    var gender: Gender = gender
        protected set

    @Column(name = "birth", columnDefinition = "date", nullable = false)
    var birth: LocalDate = birth
        protected set

    @Column(name = "image_url")
    var imageUrl: String? = imageUrl
        protected set
}
