package com.wiyb.server.core.domain.auth

import com.wiyb.server.storage.database.entity.user.constant.Role

data class CheckTokenDto(
    val role: Role
)
