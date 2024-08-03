package com.wiyb.server.storage.database.entity.golf

import com.wiyb.server.storage.database.entity.common.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "brands")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE brands SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class Brand(
    name: String,
    imageUrl: String?
) : BaseEntity() {
    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "image_url")
    var imageUrl: String? = imageUrl
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand", cascade = [CascadeType.REMOVE])
    protected val mutableEquipments: MutableList<Equipment> = mutableListOf()
    val equipments: List<Equipment> get() = mutableEquipments.toList()
}
