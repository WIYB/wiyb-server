package com.wiyb.server.core.domain.search

data class YoutubeSearchResultDto(
    val title: String,
    val description: String?,
    val channelId: String,
    val channelTitle: String,
    val thumbnailUrl: String?,
    val videoUrl: String,
    val publishedAt: String,
    val tags: List<String>? = null
)
