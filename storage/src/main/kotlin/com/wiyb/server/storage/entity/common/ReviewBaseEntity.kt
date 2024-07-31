package com.wiyb.server.storage.entity.common

import com.wiyb.server.storage.entity.User
import jakarta.persistence.Column
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.springframework.data.domain.Persistable

abstract class ReviewBaseEntity<T : Persistable<Long>>(
    user: User,
    equipment: T,
    evaluationMetric: Int,
    content: String,
    imageUrls: String? = null
) : BaseEntity() {
    @Column(name = "evaluation_metric", nullable = false)
    var evaluationMetric: Int = evaluationMetric
        protected set

    @Column(name = "content", nullable = false)
    var content: String = content
        protected set

    @Column(name = "image_urls", columnDefinition = "text")
    var imageUrls: String? = imageUrls
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User = user
        protected set

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    var equipment: T = equipment
        protected set
}
