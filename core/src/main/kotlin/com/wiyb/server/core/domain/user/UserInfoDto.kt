package com.wiyb.server.core.domain.user

import com.wiyb.server.storage.entity.User
import java.time.LocalDate
import java.time.LocalDateTime

data class UserInfoDto(
    val id: Long,
    val nickname: String,
    val gender: String,
    val birth: LocalDate,
    val imageUrl: String? = null,
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromEntity(user: User): UserInfoDto =
            UserInfoDto(
                id = user.id,
                nickname = user.nickname!!,
                gender = user.gender!!.getCode(),
                birth = user.birth!!,
                imageUrl = user.imageUrl,
                createdAt = user.createdAt
            )
    }
}
