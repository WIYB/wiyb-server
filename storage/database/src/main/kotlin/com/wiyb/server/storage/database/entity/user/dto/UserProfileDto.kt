package com.wiyb.server.storage.database.entity.user.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.database.entity.user.constant.Gender
import java.time.LocalDate
import java.time.LocalDateTime

data class UserProfileDto
    @QueryProjection
    constructor(
        val id: String,
        val nickname: String?,
        val gender: Gender?,
        val birth: LocalDate?,
        val handy: Int?,
        val height: Int?,
        val weight: Int?,
        val imageUrl: String?,
        val createdAt: LocalDateTime
    )
