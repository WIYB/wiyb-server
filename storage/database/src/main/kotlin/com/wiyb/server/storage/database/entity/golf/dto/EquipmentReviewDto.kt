package com.wiyb.server.storage.database.entity.golf.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.database.entity.user.dto.UserSimpleProfileDto
import java.time.LocalDateTime

data class EquipmentReviewDto
    @QueryProjection
    constructor(
        val id: String,
        val content: String,
        val imageUrls: List<String>?,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val user: UserSimpleProfileDto
    )
