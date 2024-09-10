package com.wiyb.server.storage.database.entity.user.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.database.entity.user.UserProfile

data class UserSimpleProfileDto
    @QueryProjection
    constructor(
        val id: String,
        val nickname: String,
        val handy: Int?,
        val height: Int?,
        val weight: Int?,
        val imageUrl: String?
    ) {
        companion object {
            fun from(userProfile: UserProfile): UserSimpleProfileDto =
                UserSimpleProfileDto(
                    id = userProfile.user.id.toString(),
                    nickname = userProfile.nickname,
                    handy = userProfile.handy,
                    height = userProfile.height,
                    weight = userProfile.weight,
                    imageUrl = userProfile.imageUrl
                )
        }
    }
