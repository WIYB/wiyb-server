package com.wiyb.server.core.domain.user

import com.wiyb.server.storage.entity.user.User
import com.wiyb.server.storage.entity.user.UserProfile
import java.time.LocalDate
import java.time.LocalDateTime

data class UserProfileDto(
    val id: String,
    val nickname: String,
    val gender: String,
    val birth: LocalDate,
    val handy: Int? = null,
    val height: Int? = null,
    val weight: Int? = null,
    val imageUrl: String? = null,
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromEntity(
            user: User,
            userProfile: UserProfile
        ): UserProfileDto =
            UserProfileDto(
                id = user.id.toString(),
                nickname = userProfile.nickname,
                gender = userProfile.gender.getCode(),
                birth = userProfile.birth,
                handy = userProfile.handy,
                height = userProfile.height,
                weight = userProfile.weight,
                imageUrl = userProfile.imageUrl,
                createdAt = userProfile.createdAt
            )
    }
}
