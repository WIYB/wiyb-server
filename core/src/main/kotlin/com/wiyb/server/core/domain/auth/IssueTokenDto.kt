package com.wiyb.server.core.domain.auth

data class IssueTokenDto(
    val accessToken: String,
    val refreshToken: String
)
