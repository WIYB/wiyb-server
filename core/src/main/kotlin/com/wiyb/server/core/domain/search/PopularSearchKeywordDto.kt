package com.wiyb.server.core.domain.search

data class PopularSearchKeywordDto(
    val keyword: String,
    val hitCount: Long
)
