package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class UserEntity(
    name: String,
    nickname: String,
    imageUrl: String?
) : BaseEntity() {
    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "nickname", nullable = false)
    var nickname: String = nickname
        protected set

    @Column(name = "image_url")
    var imageUrl: String? = imageUrl
        protected set
}
