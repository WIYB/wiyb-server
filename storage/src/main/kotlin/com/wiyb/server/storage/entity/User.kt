@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.BaseEntity
import com.wiyb.server.storage.entity.constant.user.Role
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    protected val mutableUserEquipments: MutableList<UserEquipment> = mutableListOf()
    val userEquipments: List<UserEquipment> get() = mutableUserEquipments.toList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    protected val mutableClubHeadReviews: MutableList<ClubHeadReview> = mutableListOf()
    val clubHeadReviews: List<ClubHeadReview> get() = mutableClubHeadReviews.toList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    protected val mutableClubShaftReviews: MutableList<ClubShaftReview> = mutableListOf()
    val clubShaftReviews: List<ClubShaftReview> get() = mutableClubShaftReviews.toList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    protected val mutableClubGripReviews: MutableList<ClubGripReview> = mutableListOf()
    val clubGripReviews: List<ClubGripReview> get() = mutableClubGripReviews.toList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    protected val mutableGolfBallReviews: MutableList<GolfBallReview> = mutableListOf()
    val golfBallReviews: List<GolfBallReview> get() = mutableGolfBallReviews.toList()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.REMOVE])
    protected val mutableGolfOtherEquipmentReviews: MutableList<GolfOtherEquipmentReview> = mutableListOf()
    val golfOtherEquipmentReviews: List<GolfOtherEquipmentReview> get() = mutableGolfOtherEquipmentReviews.toList()

    fun updateRole(role: Role) {
        this.role = role
    }
}
