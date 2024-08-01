package com.wiyb.server.storage.entity

import com.wiyb.server.storage.entity.common.GolfBaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
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
) : GolfBaseEntity(name, releasedYear, imageUrls) {
    @Column(name = "equipment_type", nullable = false)
    var equipmentType: String = equipmentType
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipment", cascade = [CascadeType.REMOVE])
    protected val mutableReviews: MutableList<GolfOtherEquipmentReview> = mutableListOf()
    val reviews: List<GolfOtherEquipmentReview> get() = mutableReviews.toList()

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    var brand: Brand = brand
        protected set
}
