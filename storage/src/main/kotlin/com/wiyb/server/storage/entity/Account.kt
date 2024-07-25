package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.BaseEntity
import com.wiyb.server.storage.entity.constant.SocialProvider
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "accounts")
class Account(
    socialId: String,
    socialProvider: SocialProvider,
    email: String,
    user: User
) : BaseEntity() {
    @Column(name = "social_provider", nullable = false)
    var socialProvider: SocialProvider = socialProvider
        protected set

    @Column(name = "social_id", nullable = false)
    var socialId: String = socialId
        protected set

    @Column(nullable = false)
    var email: String = email
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = user
        protected set
}
