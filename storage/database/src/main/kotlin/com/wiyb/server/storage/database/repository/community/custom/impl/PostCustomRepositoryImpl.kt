package com.wiyb.server.storage.database.repository.community.custom.impl

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.group.GroupBy.list
import com.querydsl.jpa.JPQLQuery
import com.wiyb.server.storage.database.entity.common.dto.PaginationResultDto
import com.wiyb.server.storage.database.entity.community.Post
import com.wiyb.server.storage.database.entity.community.QComment.comment
import com.wiyb.server.storage.database.entity.community.QPost.post
import com.wiyb.server.storage.database.entity.community.constant.Category
import com.wiyb.server.storage.database.entity.community.dto.PostDto
import com.wiyb.server.storage.database.entity.community.dto.PostPaginationDto
import com.wiyb.server.storage.database.entity.community.dto.QCommentDto
import com.wiyb.server.storage.database.entity.community.dto.QPostDto
import com.wiyb.server.storage.database.entity.user.dto.QUserSimpleProfileDto
import com.wiyb.server.storage.database.repository.community.custom.PostCustomRepository
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class PostCustomRepositoryImpl :
    QuerydslRepositorySupport(Post::class.java),
    PostCustomRepository {
    override fun findDetailById(id: Long): Optional<PostDto> {
        val post =
            from(post)
                .select(
                    QPostDto(
                        post.id.stringValue(),
                        post.category,
                        post.title,
                        post.content,
                        post.viewCount,
                        post.commentCount,
                        post.imageUrls,
                        post.createdAt,
                        post.updatedAt,
                        QUserSimpleProfileDto(
                            post.author.id.stringValue(),
                            post.author.nickname,
                            post.author.handy,
                            post.author.height,
                            post.author.weight,
                            post.author.imageUrl
                        ),
                        list(
                            QCommentDto(
                                comment.id.stringValue(),
                                comment.content,
                                comment.createdAt,
                                comment.updatedAt,
                                QUserSimpleProfileDto(
                                    comment.author.id.stringValue(),
                                    comment.author.nickname,
                                    comment.author.handy,
                                    comment.author.height,
                                    comment.author.weight,
                                    comment.author.imageUrl
                                ),
                                comment.replyId.stringValue()
                            )
                        )
                    )
                ).leftJoin(post.mutableComments, comment)
                .where(post.id.eq(id))
                .fetchOne()

        return Optional.ofNullable(post)
    }

    override fun findPage(parameter: PostPaginationDto): PaginationResultDto<PostDto> {
        val pageRequest = parameter.of()
        val query = paginationQuery(parameter, pageRequest)
        val page = PageImpl(query.fetch(), pageRequest, query.fetchCount())

        return PaginationResultDto.fromPage<PostDto>(parameter.contextId, page)
    }

    private fun paginationQuery(
        parameter: PostPaginationDto,
        pageRequest: PageRequest
    ): JPQLQuery<PostDto> =
        from(post)
            .select(
                QPostDto(
                    post.id.stringValue(),
                    post.category,
                    post.title,
                    null,
                    post.viewCount,
                    post.commentCount,
                    post.imageUrls,
                    post.createdAt,
                    post.updatedAt,
                    QUserSimpleProfileDto(
                        post.author.id.stringValue(),
                        post.author.nickname,
                        post.author.handy,
                        post.author.height,
                        post.author.weight,
                        post.author.imageUrl
                    ),
                    null
                )
            ).where(categoryStrategy(parameter.category))
            .groupBy(post.id)
            .orderBy(post.createdAt.desc())
            .offset(pageRequest.offset)
            .limit(pageRequest.pageSize.toLong())

    private fun categoryStrategy(category: Category): BooleanBuilder {
        val builder = BooleanBuilder()

        if (category != Category.ALL) {
            builder.and(post.category.eq(category))
        }

        return builder
    }
}
