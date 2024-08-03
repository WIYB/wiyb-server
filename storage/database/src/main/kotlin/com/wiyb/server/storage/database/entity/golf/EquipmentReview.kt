package com.wiyb.server.storage.database.entity.golf

import com.wiyb.server.storage.database.converter.StringListConverter
import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.user.User
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "equipment_reviews")
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE equipment_reviews SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class EquipmentReview(
    user: User,
    equipment: Equipment,
    evaluationMetric: Int,
    content: String,
    imageUrls: List<String>?
) : BaseEntity() {
    @Column(name = "evaluation_metric", nullable = false)
    var evaluationMetric: Int = evaluationMetric
        protected set

    @Column(name = "content", nullable = false)
    var content: String = content
        protected set

    @Convert(converter = StringListConverter::class)
    @Column(name = "image_urls", columnDefinition = "text")
    var imageUrls: List<String>? = imageUrls
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = user
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    var equipment: Equipment = equipment
        protected set
}
