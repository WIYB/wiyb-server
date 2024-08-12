package com.wiyb.server.storage.database.entity.golf.detail

import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractIron
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "irons")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE irons SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class Iron(
    equipment: Equipment,
    numbers: String? = null,
    produceType: String? = null,
    designType: String? = null,
    loftDegree: String? = null,
    loft7Degree: String? = null,
    loftPDegree: String? = null
) : BaseEntity(equipment.id),
    AbstractIron {
    // 헤드 번호들 ex) [3, 5, 7]
    @Column(name = "numbers")
    var numbers: String? = numbers
        protected set

    // 생산 타입 ex) 단조, 주조, 중공주조
    @Column(name = "produce_type")
    var produceType: String? = produceType
        protected set

    // 디자인 타입 ex) 캐비티백, 머슬백, 머슬캐비티백
    @Column(name = "design_type")
    var designType: String? = designType
        protected set

    // 로프트 각도
    @Column(name = "loft_degree")
    var loftDegree: String? = loftDegree
        protected set

    // 7번 아이언의 로프트 각도
    @Column(name = "loft_7_degree")
    var loft7Degree: String? = loft7Degree
        protected set

    // P번 아이언의 로프트 각도
    @Column(name = "loft_p_degree")
    var loftPDegree: String? = loftPDegree
        protected set

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    var equipment: Equipment = equipment
        protected set
}
