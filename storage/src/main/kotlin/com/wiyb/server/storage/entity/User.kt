@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.BaseEntity
import com.wiyb.server.storage.entity.constant.Role
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "users")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class User(
    role: Role
) : BaseEntity() {
    @Column(name = "role", nullable = false)
    var role: Role = role
        protected set

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE, CascadeType.PERSIST])
    var userProfile: UserProfile? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    protected val mutableAccounts: MutableList<Account> = mutableListOf()
    val accounts: List<Account> get() = mutableAccounts.toList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    protected val mutableAuthorizations: MutableList<Authorization> = mutableListOf()
    val authorizations: List<Authorization> get() = mutableAuthorizations.toList()

    fun addUserProfile(userProfile: UserProfile) {
        this.userProfile = userProfile
    }

    fun addAccount(account: Account) {
        mutableAccounts.add(account)
    }

    fun addAuthorization(authorizations: Authorization) {
        mutableAuthorizations.add(authorizations)
    }

    fun updateRole(role: Role) {
        this.role = role
    }
}
