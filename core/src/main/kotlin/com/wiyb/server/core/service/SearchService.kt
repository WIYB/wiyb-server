package com.wiyb.server.core.service

import com.wiyb.server.core.domain.search.PopularSearchKeywordDto
import com.wiyb.server.core.domain.search.mapper.SearchKeywordMapper
import com.wiyb.server.storage.cache.entity.SearchKeyword
import com.wiyb.server.storage.cache.repository.SearchKeywordRepository
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val searchKeywordRepository: SearchKeywordRepository
) {
    fun getPopularKeywords(): List<PopularSearchKeywordDto> {
        val keywords = searchKeywordRepository.findTop10ByOrderByWeeklyHitCountDesc()
        return SearchKeywordMapper.toList(keywords)
    }

    fun increaseHitCount(keyword: String) {
        val searchKeyword = searchKeywordRepository.findById(keyword).orElseGet { SearchKeyword(keyword) }
        searchKeyword.increase()
        searchKeywordRepository.save(searchKeyword)
    }
}
