package com.wiyb.server.storage.database.entity.golf

import com.wiyb.server.storage.database.converter.FloatListConverter
import com.wiyb.server.storage.database.converter.StringListConverter
import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.user.User
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "equipment_reviews")
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "equipment_id", "deleted_at"]) ])
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE equipment_reviews SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class EquipmentReview(
    user: User,
    equipment: Equipment,
    content: String,
    evaluationMetric: List<Float>? = null,
    imageUrls: List<String>? = null
) : BaseEntity() {
    @Column(name = "content", columnDefinition = "text", nullable = false)
    var content: String = content
        protected set

    @Convert(converter = FloatListConverter::class)
    @Column(name = "evaluation_metric")
    var evaluationMetric: List<Float>? = evaluationMetric
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
