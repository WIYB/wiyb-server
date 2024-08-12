package com.wiyb.server.storage.database.entity.golf.detail

import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractDriver
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "drivers")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE drivers SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class Driver(
    equipment: Equipment,
    loftDegree: String? = null,
    volume: Float? = null
) : BaseEntity(equipment.id),
    AbstractDriver {
    // 로프트 각도
    @Column(name = "loft_degree")
    var loftDegree: String? = loftDegree
        protected set

    // 드라이버 체적
    @Column(name = "volume")
    var volume: Float? = volume
        protected set

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    var equipment: Equipment = equipment
        protected set
}
