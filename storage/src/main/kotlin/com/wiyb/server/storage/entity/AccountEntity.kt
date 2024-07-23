package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.BaseEntity
import com.wiyb.server.storage.entity.constant.SocialType
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class AccountEntity(
    socialId: String,
    socialType: SocialType,
    email: String
) : BaseEntity() {
    @Column(name = "social_id", nullable = false)
    var socialId: String = socialId
        protected set

    @Column(name = "social_type", nullable = false)
    var socialType: SocialType = socialType
        protected set

    @Column(nullable = false)
    var email: String = email
        protected set
}
