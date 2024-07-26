package com.wiyb.server.core.domain.user

import com.wiyb.server.core.config.annotation.ValueOfEnum
import com.wiyb.server.storage.entity.constant.Gender
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import java.time.LocalDate

data class CreateProfileDto(
    @field:NotBlank(message = "nickname must not be blank")
    val nickname: String,
    @field:ValueOfEnum(enumClass = Gender::class, message = "gender value is invalid")
    val gender: Gender,
    @field:Past(message = "birth must be past")
    val birth: LocalDate
)
