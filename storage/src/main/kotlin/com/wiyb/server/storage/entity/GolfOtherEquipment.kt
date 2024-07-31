package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.GolfBaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "golf_other_equipments")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE golf_other_equipments SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class GolfOtherEquipment(
    brand: String,
    name: String,
    equipmentType: String,
    releasedYear: String,
    imageUrls: String? = null
) : GolfBaseEntity(brand, name, releasedYear, imageUrls) {
    @Column(name = "equipment_type", nullable = false)
    var equipmentType: String = equipmentType
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipment", cascade = [CascadeType.REMOVE])
    protected val mutableGolfOtherEquipmentReviews: MutableList<GolfOtherEquipmentReview> = mutableListOf()
    val golfOtherEquipmentReviews: List<GolfOtherEquipmentReview> get() = mutableGolfOtherEquipmentReviews.toList()
}
