package com.wiyb.server.storage.database.entity.golf

import com.wiyb.server.storage.database.converter.StringListConverter
import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "equipments")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE equipments SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class Equipment(
    brand: Brand,
    type: EquipmentType,
    name: String,
    releasedYear: String? = null,
    imageUrls: List<String>? = null
) : BaseEntity() {
    @Column(name = "type", nullable = false)
    var type: EquipmentType = type
        protected set

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "view_count", nullable = false)
    var viewCount: Long = 0
        protected set

    @Column(name = "released_year")
    var releasedYear: String? = releasedYear
        protected set

    @Convert(converter = StringListConverter::class)
    @Column(name = "image_urls", columnDefinition = "text")
    var imageUrls: List<String>? = imageUrls
        protected set

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "equipment", cascade = [CascadeType.REMOVE])
    var evaluatedMetric: EquipmentEvaluatedMetric? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipment", cascade = [CascadeType.REMOVE])
    protected val mutableEquipmentReviews: MutableList<EquipmentReview> = mutableListOf()
    val equipmentReviews: List<EquipmentReview> get() = mutableEquipmentReviews.toList()

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    var brand: Brand = brand
        protected set

    fun increase(count: Long) {
        viewCount += count
    }
}
