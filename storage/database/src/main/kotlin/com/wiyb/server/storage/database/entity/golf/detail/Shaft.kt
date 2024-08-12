package com.wiyb.server.storage.database.entity.golf.detail

import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractShaft
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "shafts")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE shafts SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class Shaft(
    equipment: Equipment,
    weight: String? = null,
    strength: String? = null,
    kickPoint: String? = null,
    torque: String? = null,
    texture: String? = null,
    tipDiameter: String? = null,
    buttDiameter: String? = null,
    spin: String? = null,
    launch: String? = null
) : BaseEntity(equipment.id),
    AbstractShaft {
    // 무게
    @Column(name = "weight")
    var weight: String? = weight
        protected set

    // 강도
    @Column(name = "strength")
    var strength: String? = strength
        protected set

    // 킥 포인트
    @Column(name = "kick_point")
    var kickPoint: String? = kickPoint
        protected set

    // 토크
    @Column(name = "torque")
    var torque: String? = torque
        protected set

    // 질감
    @Column(name = "texture")
    var texture: String? = texture
        protected set

    // 티프 직경
    @Column(name = "tip_diameter")
    var tipDiameter: String? = tipDiameter
        protected set

    // 버트 직경
    @Column(name = "butt_diameter")
    var buttDiameter: String? = buttDiameter
        protected set

    // 스핀
    @Column(name = "spin")
    var spin: String? = spin
        protected set

    // 런치
    @Column(name = "launch")
    var launch: String? = launch
        protected set

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    var equipment: Equipment = equipment
        protected set
}
