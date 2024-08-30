package com.wiyb.server.storage.database.entity.user.dto

import com.querydsl.core.annotations.QueryProjection

data class UserSimpleProfileDto
    @QueryProjection
    constructor(
        val id: String,
        val nickname: String,
        val handy: Int?,
        val height: Int?,
        val weight: Int?,
        val imageUrl: String?
    )
