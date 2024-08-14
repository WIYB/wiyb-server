package com.wiyb.server.core.domain.user

import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

data class UserIdDto(
    @field:Size(min = 18, max = 18, message = "invalid user id")
    @field:Positive
    val userId: String?
)
