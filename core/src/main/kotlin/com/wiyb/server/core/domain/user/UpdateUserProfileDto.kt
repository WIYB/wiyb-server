package com.wiyb.server.core.domain.user

import jakarta.validation.constraints.NotBlank

data class UpdateUserProfileDto(
    @field:NotBlank(message = "nickname must not be blank")
    val nickname: String
)
