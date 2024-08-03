package com.wiyb.server.storage.database.entity.user

import com.wiyb.server.storage.database.entity.auth.Account
import com.wiyb.server.storage.database.entity.auth.Authorization
import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.EquipmentReview
import com.wiyb.server.storage.database.entity.user.constant.Role
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    protected val mutableUserEquipments: MutableList<UserEquipment> = mutableListOf()
    val userEquipments: List<UserEquipment> get() = mutableUserEquipments.toList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    protected val mutableEquipmentReviews: MutableList<EquipmentReview> = mutableListOf()
    val equipmentReview: List<EquipmentReview> get() = mutableEquipmentReviews.toList()

    fun updateRole(role: Role) {
        this.role = role
    }
}
