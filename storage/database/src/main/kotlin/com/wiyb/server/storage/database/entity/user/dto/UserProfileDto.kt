package com.wiyb.server.storage.database.entity.user.dto

import com.querydsl.core.annotations.QueryProjection
import com.wiyb.server.storage.database.entity.user.User
import com.wiyb.server.storage.database.entity.user.UserProfile
import java.time.LocalDate
import java.time.LocalDateTime

data class UserProfileDto
    @QueryProjection
    constructor(
        private val user: User,
        private val userProfile: UserProfile?
    ) {
        val id: String = user.id.toString()
        val nickname: String? = userProfile?.nickname
        val gender: String? = userProfile?.gender?.getCode()
        val birth: LocalDate? = userProfile?.birth
        val handy: Int? = userProfile?.handy
        val height: Int? = userProfile?.height
        val weight: Int? = userProfile?.weight
        val imageUrl: String? = userProfile?.imageUrl
        val createdAt: LocalDateTime = user.createdAt
    }
