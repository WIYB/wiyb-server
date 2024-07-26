package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLDelete

@Entity(name = "authorizations")
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
