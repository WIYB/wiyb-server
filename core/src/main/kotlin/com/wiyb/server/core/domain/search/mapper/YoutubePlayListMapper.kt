package com.wiyb.server.core.domain.search.mapper

import com.wiyb.server.core.domain.search.YoutubeSearchResultDto
import com.wiyb.server.storage.cache.entity.YoutubePlayList

class YoutubePlayListMapper {
    companion object {
        fun to(
            relatedId: Long,
            dto: YoutubeSearchResultDto
        ): YoutubePlayList =
            YoutubePlayList(
                relatedId = relatedId,
                title = dto.title,
                description = dto.description,
                channelId = dto.channelId,
                channelTitle = dto.channelTitle,
                thumbnailUrl = dto.thumbnailUrl,
                videoUrl = dto.videoUrl,
                publishedAt = dto.publishedAt,
                tags = dto.tags
            )

        fun to(dto: YoutubePlayList): YoutubeSearchResultDto =
            YoutubeSearchResultDto(
                title = dto.title,
                description = dto.description,
                channelId = dto.channelId,
                channelTitle = dto.channelTitle,
                thumbnailUrl = dto.thumbnailUrl,
                videoUrl = dto.videoUrl,
                publishedAt = dto.publishedAt,
                tags = dto.tags
            )

        fun toList(
            relatedId: Long,
            dtoList: List<YoutubeSearchResultDto>
        ): List<YoutubePlayList> = dtoList.map { to(relatedId, it) }

        fun toList(dtoList: List<YoutubePlayList>): List<YoutubeSearchResultDto> = dtoList.map { to(it) }
    }
}
