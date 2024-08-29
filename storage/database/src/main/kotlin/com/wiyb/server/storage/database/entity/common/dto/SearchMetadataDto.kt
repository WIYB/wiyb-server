package com.wiyb.server.storage.database.entity.common.dto

data class SearchMetadataDto(
    val contextId: String,
    val offset: Int,
    val totalOffset: Int,
    val size: Int,
    val totalSize: Int,
    val isLast: Boolean,
    val isEmpty: Boolean
)
