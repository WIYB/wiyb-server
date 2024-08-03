package com.wiyb.server.storage.database.entity.user

import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.Equipment
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "user_equipments")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE user_equipments SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class UserEquipment(
    user: User,
    equipment: Equipment
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = user
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    var equipment: Equipment = equipment
        protected set
}
