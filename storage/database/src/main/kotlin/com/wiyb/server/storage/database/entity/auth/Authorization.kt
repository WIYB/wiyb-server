package com.wiyb.server.storage.database.entity.auth

import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "authorizations")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE authorizations SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class Authorization(
    sessionId: String,
    user: User
) : BaseEntity() {
    @Column(name = "session_id", nullable = false)
    var sessionId: String = sessionId
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = user
        protected set
}
