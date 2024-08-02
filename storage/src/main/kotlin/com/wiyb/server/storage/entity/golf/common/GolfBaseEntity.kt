package com.wiyb.server.storage.entity.golf.common

import com.wiyb.server.storage.entity.common.BaseEntity
import com.wiyb.server.storage.entity.golf.Brand
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class GolfBaseEntity<T : BaseEntity>(
    brand: Brand,
    name: String,
    releasedYear: String,
    imageUrls: String? = null
) : BaseEntity() {
    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "released_year", nullable = false)
    var releasedYear: String = releasedYear
        protected set

    @Column(name = "image_urls", columnDefinition = "text")
    var imageUrls: String? = imageUrls
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", foreignKey = ForeignKey(), nullable = false)
    var brand: Brand = brand
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "equipment", cascade = [CascadeType.REMOVE])
    protected val mutableReviews: MutableList<T> = mutableListOf()
    val reviews: List<T> get() = mutableReviews.toList()
}
