package com.wiyb.server.storage.cache.entity

import com.github.f4b6a3.tsid.TsidCreator
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.io.Serializable

@RedisHash("youtube_playlist")
data class YoutubePlayList(
    @Indexed
    val relatedId: Long,
    val title: String,
    val description: String?,
    val channelId: String,
    val channelTitle: String,
    val thumbnailUrl: String?,
    val videoUrl: String,
    val publishedAt: String,
    val tags: List<String>? = null
) : Serializable {
    @Id
    val id: Long = TsidCreator.getTsid().toLong()
}
