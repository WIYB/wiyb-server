package com.wiyb.server.storage.database.entity.golf.detail

import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractWood
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "woods")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE woods SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class Wood(
    equipment: Equipment,
    loftDegree: String? = null,
    numbers: String? = null
) : BaseEntity(equipment.id),
    AbstractWood {
    // 로프트 각도
    @Column(name = "loft_degree")
    var loftDegree: String? = loftDegree
        protected set

    // 헤드 번호들 ex) [3, 5, 7]
    @Column(name = "numbers")
    var numbers: String? = numbers
        protected set

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    var equipment: Equipment = equipment
        protected set
}
