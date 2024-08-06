package com.wiyb.server.core.domain.user

import com.wiyb.server.core.config.annotation.ValueOfEnum
import com.wiyb.server.storage.database.entity.user.User
import com.wiyb.server.storage.database.entity.user.UserProfile
import com.wiyb.server.storage.database.entity.user.constant.Gender
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import org.hibernate.validator.constraints.Length
import java.time.LocalDate

data class CreateUserProfileDto(
    @field:Length(min = 2, max = 10, message = "nickname must be at least 2 characters and at most 10 characters")
    @field:NotBlank(message = "nickname must not be blank")
    val nickname: String,
    @field:ValueOfEnum(enumClass = Gender::class, message = "gender value is invalid")
    @field:NotNull(message = "gender must not be null")
    val gender: String,
    @field:Past(message = "birth must be past")
    @field:NotNull(message = "birth must not be null")
    val birth: LocalDate
) {
    fun toEntity(user: User): UserProfile =
        UserProfile(
            user = user,
            nickname = nickname,
            gender = enumValueOf<Gender>(gender.uppercase()),
            birth = birth
        )
}
