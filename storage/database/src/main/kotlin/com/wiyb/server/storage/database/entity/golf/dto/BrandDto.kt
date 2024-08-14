package com.wiyb.server.storage.database.entity.golf.dto

import com.querydsl.core.annotations.QueryProjection

data class BrandDto
    @QueryProjection
    constructor(
        val id: String,
        val name: String,
        val nameKo: String?,
        val logoImageUrl: String?
    )
