package com.wiyb.server.storage.entity.common

import jakarta.persistence.Column

abstract class ReviewBaseEntity<T : BaseEntity>(
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
}
