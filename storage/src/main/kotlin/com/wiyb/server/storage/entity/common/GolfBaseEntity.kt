package com.wiyb.server.storage.entity.common

import jakarta.persistence.Column

abstract class GolfBaseEntity(
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
}
