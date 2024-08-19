package com.wiyb.server.storage.database.entity.golf.dto

data class SearchMetadataDto(
    val contextId: String,
    val offset: Int,
    val total: Int,
    val size: Int,
    val isLast: Boolean,
    val isEmpty: Boolean
)
