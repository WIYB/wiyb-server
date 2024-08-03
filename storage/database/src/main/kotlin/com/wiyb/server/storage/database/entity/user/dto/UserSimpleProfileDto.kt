package com.wiyb.server.storage.database.entity.user.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.database.entity.user.User
import com.wiyb.server.storage.database.entity.user.UserProfile

data class UserSimpleProfileDto
    @QueryProjection
    constructor(
        private val user: User,
        private val userProfile: UserProfile
    ) {
        val id: String = user.id.toString()
        val nickname: String = userProfile.nickname
        val handy: Int? = userProfile.handy
        val height: Int? = userProfile.height
        val weight: Int? = userProfile.weight
        val imageUrl: String? = userProfile.imageUrl
    }
