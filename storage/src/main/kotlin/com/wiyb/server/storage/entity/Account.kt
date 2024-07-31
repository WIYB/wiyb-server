package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.BaseEntity
import com.wiyb.server.storage.entity.constant.auth.SocialProvider
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "accounts")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE accounts SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = user
        protected set
}
