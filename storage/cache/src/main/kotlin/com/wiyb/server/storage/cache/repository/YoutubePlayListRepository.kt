package com.wiyb.server.storage.cache.repository

import com.wiyb.server.storage.cache.entity.YoutubePlayList
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.ListCrudRepository

interface YoutubePlayListRepository :
    CrudRepository<YoutubePlayList, Long>,
    ListCrudRepository<YoutubePlayList, Long> {
    fun findAllByRelatedId(relatedId: Long): List<YoutubePlayList>
}
