package com.wiyb.server.storage.database.entity.golf.detail

import com.wiyb.server.storage.database.converter.StringListConverter
import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractWedge
import jakarta.persistence.Column
import jakarta.persistence.Convert
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
    produceType: String? = null,
    model: List<String>? = null,
    loftDegree: List<String>? = null,
    bounce: List<String>? = null,
    grind: List<String>? = null
) : BaseEntity(equipment.id),
    AbstractWedge {
    // 생산 타입
    @Column(name = "produce_type")
    var produceType: String? = produceType
        protected set

    // 세부 모델
    @Convert(converter = StringListConverter::class)
    @Column(name = "model")
    var model: List<String>? = model
        protected set

    // 로프트 각도
    @Convert(converter = StringListConverter::class)
    @Column(name = "loft_degree")
    var loftDegree: List<String>? = loftDegree
        protected set

    // 바운스
    @Convert(converter = StringListConverter::class)
    @Column(name = "bounce")
    var bounce: List<String>? = bounce
        protected set

    // 그라인드
    @Convert(converter = StringListConverter::class)
    @Column(name = "grind")
    var grind: List<String>? = grind
        protected set

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    var equipment: Equipment = equipment
        protected set
}
