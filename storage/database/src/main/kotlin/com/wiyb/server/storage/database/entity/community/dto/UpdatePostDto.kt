package com.wiyb.server.storage.database.entity.community.dto

data class UpdatePostDto(
    val title: String?,
    val content: String?,
    val imageUrls: List<String>?
)
