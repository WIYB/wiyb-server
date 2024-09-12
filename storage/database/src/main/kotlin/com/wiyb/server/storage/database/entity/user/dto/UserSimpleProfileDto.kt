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
            fun from(userProfile: UserProfile? = null): UserSimpleProfileDto {
                if (userProfile == null) {
                    return deletedUser()
                }

                return UserSimpleProfileDto(
                    id = userProfile.id.toString(),
                    nickname = userProfile.nickname,
                    handy = userProfile.handy,
                    height = userProfile.height,
                    weight = userProfile.weight,
                    imageUrl = userProfile.imageUrl
                )
            }

            private fun deletedUser(id: String? = null): UserSimpleProfileDto =
                UserSimpleProfileDto(
                    id = id ?: "",
                    nickname = "탈퇴한 사용자",
                    handy = null,
                    height = null,
                    weight = null,
                    imageUrl = null
                )
        }
    }
