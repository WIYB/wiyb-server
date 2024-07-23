package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.BaseEntity
import com.wiyb.server.storage.entity.constant.Gender
import com.wiyb.server.storage.entity.constant.Role
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import java.time.LocalDate

@Entity(name = "users")
class User(
    role: Role,
    name: String,
    nickname: String? = null,
    gender: Gender? = null,
    birth: LocalDate? = null,
    imageUrl: String? = null
) : BaseEntity() {
    @Column(name = "role", nullable = false)
    var role: Role = role
        protected set

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "nickname")
    var nickname: String? = nickname
        protected set

    @Column(name = "gender")
    var gender: Gender? = gender
        protected set

    @Column(name = "birth", columnDefinition = "date")
    var birth: LocalDate? = birth
        protected set

    @Column(name = "image_url")
    var imageUrl: String? = imageUrl
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    protected val mutableAccounts: MutableList<Account> = mutableListOf()
    val accounts: List<Account> get() = mutableAccounts.toList()

    fun addAccount(account: Account) {
        mutableAccounts.add(account)
    }
}
