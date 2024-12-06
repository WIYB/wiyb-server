package com.wiyb.server.core.domain.community

import com.wiyb.server.storage.database.entity.community.dto.CommentDto
import com.wiyb.server.storage.database.entity.user.dto.UserSimpleProfileDto
import java.time.LocalDateTime

data class CommentDetailDto(
    val id: String,
    val replyTo: String? = null,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val author: UserSimpleProfileDto,
    var replies: List<CommentDetailDto>? = null
) {
    companion object {
        fun from(dtoList: List<CommentDto>): List<CommentDetailDto> {
            val store: MutableMap<String, MutableList<String>> = mutableMapOf()

            dtoList.forEach { dto ->
                if (!dto.replyTo.isNullOrBlank()) {
                    store[dto.replyTo]?.add(dto.id) ?: run {
                        store[dto.replyTo!!] = mutableListOf(dto.id)
                    }
                } else if (!store.containsKey(dto.id)) {
                    store[dto.id] = mutableListOf()
                }
            }

            return store.entries
                .map { entry ->
                    val dto = dtoList.find { it.id == entry.key }!!
                    val comment = generate(dto)

                    comment.replies =
                        entry.value
                            .map { replyId ->
                                val replyDto = dtoList.find { it.id == replyId }!!
                                generate(replyDto)
                            }.sortedBy { it.id.toLong() }

                    comment
                }.sortedBy { it.id.toLong() }
        }

        private fun generate(dto: CommentDto): CommentDetailDto {
            val content = dto.deletedAt?.let { "삭제된 댓글입니다." } ?: dto.content

            return CommentDetailDto(
                id = dto.id,
                replyTo = dto.replyTo,
                content = content,
                createdAt = dto.createdAt,
                updatedAt = dto.updatedAt,
                author = dto.author
            )
        }
    }
}
