package com.wiyb.server.storage.database.entity.golf.detail

import com.wiyb.server.storage.database.converter.StringListConverter
import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractDriver
import jakarta.persistence.Column
import jakarta.persistence.Convert
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
    volume: Float? = null,
    loftDegree: List<String>? = null,
    isLoftChangeable: Boolean? = null,
    isWeightChangeable: Boolean? = null,
    isWeightMovable: Boolean? = null
) : BaseEntity(equipment.id),
    AbstractDriver {
    // 드라이버 체적
    @Column(name = "volume")
    var volume: Float? = volume
        protected set

    // 로프트 각도
    @Convert(converter = StringListConverter::class)
    @Column(name = "loft_degree")
    var loftDegree: List<String>? = loftDegree
        protected set

    // 로프트 변경 가능 여부
    @Column(name = "is_loft_changeable")
    var isLoftChangeable: Boolean? = isLoftChangeable
        protected set

    // 무게추 변경 가능 여부
    @Column(name = "is_weight_changeable")
    var isWeightChangeable: Boolean? = isWeightChangeable
        protected set

    @Column(name = "is_weight_movable")
    var isWeightMovable: Boolean? = isWeightMovable
        protected set

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    var equipment: Equipment = equipment
        protected set
}
