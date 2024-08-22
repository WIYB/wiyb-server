package com.wiyb.server.core.service

import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import com.wiyb.server.core.domain.search.YoutubeSearchDto
import com.wiyb.server.core.domain.search.YoutubeSearchResultDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Collections

@Service
class YoutubeService(
    @Value("\${youtube.api.key}")
    private val key: String
) {
    fun search(dto: YoutubeSearchDto): List<YoutubeSearchResultDto> {
        val youtube =
            YouTube
                .Builder(NetHttpTransport(), GsonFactory(), HttpRequestInitializer { })
                .build()
        val search = youtube.search().list(Collections.singletonList("id,snippet"))

        search.key = key
        search.q = "${dto.brand} ${dto.type}|${dto.keyword.replace(" ", "")}"
        search.order = "relevance"
        search.regionCode = "KR"
        search.relevanceLanguage = "ko"
        search.type = listOf("video")
        search.maxResults = 5

        val searchResponse = search.execute()

        return List(searchResponse.items.size) { index ->
            val videoId = searchResponse.items[index].id.videoId
            val snippet = searchResponse.items[index].snippet

            YoutubeSearchResultDto(
                title = snippet.title,
                description = snippet.description,
                channelId = snippet.channelId,
                channelTitle = snippet.channelTitle,
                thumbnailUrl = snippet.thumbnails.high.url,
                videoUrl = "https://www.youtube.com/watch?v=$videoId",
                publishedAt = snippet.publishedAt.toString()
            )
        }
    }
}
