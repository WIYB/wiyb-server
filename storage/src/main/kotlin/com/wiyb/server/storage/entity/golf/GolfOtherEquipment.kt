package com.wiyb.server.storage.entity.golf

import com.wiyb.server.storage.entity.golf.common.GolfBaseEntity
import com.wiyb.server.storage.entity.review.GolfOtherEquipmentReview
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "golf_other_equipments")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE golf_other_equipments SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class GolfOtherEquipment(
    brand: Brand,
    name: String,
    equipmentType: String,
    releasedYear: String,
    imageUrls: String? = null
) : GolfBaseEntity<GolfOtherEquipmentReview>(brand, name, releasedYear, imageUrls) {
    @Column(name = "equipment_type", nullable = false)
    var equipmentType: String = equipmentType
        protected set
}
