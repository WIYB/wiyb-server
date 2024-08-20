package com.wiyb.server.core.domain.search.mapper

import com.wiyb.server.core.domain.search.PopularSearchKeywordDto
import com.wiyb.server.storage.cache.entity.SearchKeyword

class SearchKeywordMapper {
    companion object {
        fun to(searchKeyword: SearchKeyword): PopularSearchKeywordDto =
            PopularSearchKeywordDto(
                keyword = searchKeyword.keyword,
                hitCount = searchKeyword.weeklyHitCount
            )

        fun toList(searchKeywords: List<SearchKeyword>): List<PopularSearchKeywordDto> = searchKeywords.map { to(it) }
    }
}
