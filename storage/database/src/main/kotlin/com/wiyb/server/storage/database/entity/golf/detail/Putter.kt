package com.wiyb.server.storage.database.entity.golf.detail

import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractPutter
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "putters")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE putters SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class Putter(
    equipment: Equipment,
    loftDegree: String? = null,
    weight: String? = null,
    neckShape: String? = null
) : BaseEntity(equipment.id),
    AbstractPutter {
    // 로프트 각도
    @Column(name = "loft_degree")
    var loftDegree: String? = loftDegree
        protected set

    // 무게
    @Column(name = "weight")
    var weight: String? = weight
        protected set

    // 넥 형태
    @Column(name = "neck_shape")
    var neckShape: String? = neckShape
        protected set

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    var equipment: Equipment = equipment
        protected set
}
