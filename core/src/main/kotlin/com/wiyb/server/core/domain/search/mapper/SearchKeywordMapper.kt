package com.wiyb.server.core.domain.search.mapper

import com.wiyb.server.core.domain.search.PopularSearchKeywordDto
import com.wiyb.server.core.domain.search.YoutubeSearchDto
import com.wiyb.server.storage.cache.entity.SearchKeyword
import com.wiyb.server.storage.database.entity.golf.constant.BrandName
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto

class SearchKeywordMapper {
    companion object {
        fun to(searchKeyword: SearchKeyword): PopularSearchKeywordDto =
            PopularSearchKeywordDto(
                keyword = searchKeyword.keyword,
                hitCount = searchKeyword.weeklyHitCount
            )

        fun to(from: EquipmentDto): YoutubeSearchDto =
            YoutubeSearchDto(
                keyword = from.name,
                brand = BrandName.find(from.brand)!!.getCodeKo()!!,
                type = from.type.getCodeKo()
            )

        fun toList(searchKeywords: List<SearchKeyword>): List<PopularSearchKeywordDto> = searchKeywords.map { to(it) }
    }
}
