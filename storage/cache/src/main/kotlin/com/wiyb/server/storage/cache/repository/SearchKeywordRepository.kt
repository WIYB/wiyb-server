package com.wiyb.server.storage.cache.repository

import com.wiyb.server.storage.cache.entity.SearchKeyword
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.ListCrudRepository

interface SearchKeywordRepository :
    CrudRepository<SearchKeyword, String>,
    ListCrudRepository<SearchKeyword, String> {
    fun findTop10ByOrderByWeeklyHitCountDesc(): List<SearchKeyword>
}
