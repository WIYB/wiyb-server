package com.wiyb.server.storage.database.entity.golf.detail

import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractGrip
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "grips")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE grips SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class Grip(
    equipment: Equipment,
    weight: String? = null,
    size: String? = null,
    coreSize: String? = null,
    feel: String? = null,
    material: String? = null,
    torque: String? = null,
    diameter: String? = null
) : BaseEntity(equipment.id),
    AbstractGrip {
    // 무게
    @Column(name = "weight")
    var weight: String? = weight
        protected set

    // 사이즈
    @Column(name = "size")
    var size: String? = size
        protected set

    // 코어 사이즈
    @Column(name = "core_size")
    var coreSize: String? = coreSize
        protected set

    // 감촉
    @Column(name = "feel")
    var feel: String? = feel
        protected set

    // 재질
    @Column(name = "material")
    var material: String? = material
        protected set

    // 토크
    @Column(name = "torque")
    var torque: String? = torque
        protected set

    // 지름
    @Column(name = "diameter")
    var diameter: String? = diameter
        protected set

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    var equipment: Equipment = equipment
        protected set
}
