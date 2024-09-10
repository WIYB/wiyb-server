package com.wiyb.server.core.domain.community

import com.wiyb.server.storage.database.entity.community.dto.CommentDto
import com.wiyb.server.storage.database.entity.user.dto.UserSimpleProfileDto
import java.time.LocalDateTime

data class CommentDetailDto(
    val id: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val author: UserSimpleProfileDto,
    val replies: List<CommentDetailDto>? = null
) {
    companion object {
        fun from(dtoList: List<CommentDto>): List<CommentDetailDto> {
            val store: MutableMap<String, MutableList<String>> = mutableMapOf()

            dtoList.forEach { dto ->
                if (dto.replyId.isNullOrBlank() && !store.containsKey(dto.id)) {
                    store[dto.id] = mutableListOf()
                } else if (!dto.replyId.isNullOrBlank()) {
                    store[dto.id]?.add(dto.replyId!!) ?: run {
                        store[dto.id] = mutableListOf(dto.replyId!!)
                    }
                }
            }

            return store.entries
                .map { entry ->
                    val dto = dtoList.find { it.id == entry.key }!!

                    CommentDetailDto(
                        id = dto.id,
                        content = dto.content,
                        createdAt = dto.createdAt,
                        updatedAt = dto.updatedAt,
                        author = dto.author,
                        replies =
                            entry.value
                                .map { replyId ->
                                    val replyDto = dtoList.find { it.id == replyId }!!

                                    CommentDetailDto(
                                        id = replyDto.id,
                                        content = replyDto.content,
                                        createdAt = replyDto.createdAt,
                                        updatedAt = replyDto.updatedAt,
                                        author = replyDto.author
                                    )
                                }.sortedBy { it.createdAt }
                    )
                }.sortedBy { it.createdAt }
        }
    }
}
