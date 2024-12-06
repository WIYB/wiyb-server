package com.wiyb.server.core.domain.search

// todo: 도메인 모듈 분리하면 crud repository에서 projection으로 불러오는 방법 시도해보자
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
