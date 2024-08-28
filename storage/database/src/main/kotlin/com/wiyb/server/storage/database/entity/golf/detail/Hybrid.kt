package com.wiyb.server.storage.database.entity.golf.detail

import com.wiyb.server.storage.database.converter.StringListConverter
import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractHybrid
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

// utility, hybrid 둘 다 통용되는 이름임
@Entity(name = "hybrids")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE hybrids SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class Hybrid(
    equipment: Equipment,
    numbers: List<String>? = null,
    loftDegree: List<String>? = null,
    lieAngle: List<String>? = null,
    isLoftChangeable: Boolean? = null,
    isWeightChangeable: Boolean? = null
) : BaseEntity(equipment.id),
    AbstractHybrid {
    // 헤드 번호들 ex) [3, 5, 7]
    @Convert(converter = StringListConverter::class)
    @Column(name = "numbers")
    var numbers: List<String>? = numbers
        protected set

    // 로프트 각도
    @Convert(converter = StringListConverter::class)
    @Column(name = "loft_degree")
    var loftDegree: List<String>? = loftDegree
        protected set

    // 라이각
    @Convert(converter = StringListConverter::class)
    @Column(name = "lie_angle")
    var lieAngle: List<String>? = lieAngle
        protected set

    // 로프트 변경 가능 여부
    @Column(name = "is_loft_changeable")
    var isLoftChangeable: Boolean? = isLoftChangeable
        protected set

    // 무게추 변경 가능 여부
    @Column(name = "is_weight_changeable")
    var isWeightChangeable: Boolean? = isWeightChangeable
        protected set

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    var equipment: Equipment = equipment
        protected set
}
