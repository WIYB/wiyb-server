package com.wiyb.server.storage.database.entity.golf.detail

import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractWedge
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "wedges")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE wedges SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class Wedge(
    equipment: Equipment,
    loftDegree: String? = null,
    produceType: String? = null,
    bounce: String? = null,
    grind: String? = null
) : BaseEntity(equipment.id),
    AbstractWedge {
    // 로프트 각도
    @Column(name = "loft_degree")
    var loftDegree: String? = loftDegree
        protected set

    // 생산 타입
    @Column(name = "produce_type")
    var produceType: String? = produceType
        protected set

    // 바운스
    @Column(name = "bounce")
    var bounce: String? = bounce
        protected set

    // 그라인드
    @Column(name = "grind")
    var grind: String? = grind
        protected set

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    var equipment: Equipment = equipment
        protected set
}
