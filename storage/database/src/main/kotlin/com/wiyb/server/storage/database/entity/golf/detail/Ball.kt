package com.wiyb.server.storage.database.entity.golf.detail

import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.detail.common.AbstractBall
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "balls")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE balls SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class Ball(
    equipment: Equipment,
    piece: String? = null,
    spin: String? = null,
    launch: String? = null,
    feel: String? = null,
    dimple: String? = null,
    texture: String? = null
) : BaseEntity(equipment.id),
    AbstractBall {
    // 개수
    @Column(name = "piece")
    var piece: String? = piece
        protected set

    // 스핀
    @Column(name = "spin")
    var spin: String? = spin
        protected set

    // 발사각
    @Column(name = "launch")
    var launch: String? = launch
        protected set

    // 감촉
    @Column(name = "feel")
    var feel: String? = feel
        protected set

    // 딤플
    @Column(name = "dimple")
    var dimple: String? = dimple
        protected set

    // 질감
    @Column(name = "texture")
    var texture: String? = texture
        protected set

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    var equipment: Equipment = equipment
        protected set
}
