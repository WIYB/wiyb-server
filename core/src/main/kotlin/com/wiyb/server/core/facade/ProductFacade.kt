package com.wiyb.server.core.facade

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.core.domain.product.PostProductReviewDto
import com.wiyb.server.core.domain.product.ProductDetailDto
import com.wiyb.server.core.domain.product.ProductReviewDto
import com.wiyb.server.core.domain.product.ProductReviewLikePathDto
import com.wiyb.server.core.domain.product.mapper.ProductMapper
import com.wiyb.server.core.domain.search.mapper.SearchKeywordMapper
import com.wiyb.server.core.service.BrandService
import com.wiyb.server.core.service.EquipmentService
import com.wiyb.server.core.service.UserService
import com.wiyb.server.core.service.YoutubeService
import com.wiyb.server.storage.database.entity.common.dto.PaginationResultDto
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentReviewDto
import com.wiyb.server.storage.database.entity.golf.dto.ReviewPaginationDto
import jakarta.transaction.Transactional
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class ProductFacade(
    private val userService: UserService,
    private val equipmentService: EquipmentService,
    private val brandService: BrandService,
    private val youtubeService: YoutubeService
) {
    fun findBrandList() = brandService.findBrandList()

    fun getProductReviews(paginationDto: ReviewPaginationDto): PaginationResultDto<EquipmentReviewDto> {
        val session = SecurityContextHolder.getContext().authentication
        val result = equipmentService.findReviewWithPagination(paginationDto)

        if (session.principal != "anonymousUser" && !session.authorities.stream().anyMatch { it.authority.equals("ROLE_GUEST") }) {
            val userId = userService.findIdBySessionId(session.name)
            val likeIds =
                equipmentService.findLikeByForeign(userId = userId, equipmentReviewIds = result.content.map { it.id.toLong() })

            result.content.forEach { review ->
                review.isLiked = likeIds.contains(review.id.toLong())
            }
        }

        return result
    }

    fun getProductDetail(
        productId: Long,
        type: EquipmentType
    ): ProductDetailDto {
        val session = SecurityContextHolder.getContext().authentication
        val equipmentDto = equipmentService.findOneWithDetailById(productId, type)
        var likeIds: List<Long>? = null
        var isBookmarked = false

        // todo: 결과 캐싱(일주일? 하루?) 및 캐싱된 결과가 있을 경우 캐싱된 결과 반환
        val youtubeResults = youtubeService.search(SearchKeywordMapper.to(equipmentDto))
        val reviews = equipmentService.findSimpleReviewByEquipmentId(productId)

        // todo: Spring Security에서 static 메서드로 캡슐화
        if (session.principal != "anonymousUser" && !session.authorities.stream().anyMatch { it.authority.equals("ROLE_GUEST") }) {
            val userId = userService.findIdBySessionId(session.name)

            equipmentService
                .findLikeByForeign(userId = userId, equipmentReviewIds = reviews.map { it.id.toLong() })
                .also { likeIds = it }
            equipmentService
                .isAlreadyBookmarkedByUser(
                    userId = userId,
                    equipmentId = productId
                ).also { isBookmarked = it }
        }

        val productReviews = ProductReviewDto.from(reviews, likeIds)
        val productDetail = ProductMapper.to(equipmentDto, youtubeResults)

        productDetail.reviews = productReviews
        productDetail.isBookmarked = isBookmarked

        return productDetail
    }

    @Transactional
    fun postProductReview(
        productId: Long,
        dto: PostProductReviewDto
    ) {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val user = userService.findBySessionId(sessionId)
        val equipment = equipmentService.findOneById(productId)

        if (equipmentService.isAlreadyReviewedByUser(productId, user.id)) {
            throw CommonException(ErrorCode.ALREADY_REVIEWED)
        }

        val review = dto.toEntity(user, equipment)
        equipmentService.postProductReview(review)

        equipment.addEvaluationMetric(review.evaluationMetric)
        equipmentService.saveEquipment(equipment)
    }

    @Transactional
    fun likeProductReview(dto: ProductReviewLikePathDto) {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val user = userService.findBySessionId(sessionId)
        val review = equipmentService.findReviewById(dto.reviewId)

        if (equipmentService.isAlreadyLikedReview(userId = user.id, equipmentReviewId = review.id)) {
            throw CommonException(ErrorCode.ALREADY_LIKED_REVIEW)
        }

        equipmentService.increaseReviewLikeCount(review)
        equipmentService.likeProductReview(user, review)
    }

    @Transactional
    fun unlikeProductReview(dto: ProductReviewLikePathDto) {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val user = userService.findBySessionId(sessionId)
        val review = equipmentService.findReviewById(dto.reviewId)
        val likes = equipmentService.findLikeByForeign(userId = user.id, equipmentReviewId = review.id)

        if (likes.isEmpty()) {
            throw CommonException(ErrorCode.NOT_LIKED_REVIEW)
        }

        equipmentService.decreaseReviewLikeCount(review)
        equipmentService.unlikeProductReview(likes)
    }

    fun bookmarkProduct(productId: Long) {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val user = userService.findBySessionId(sessionId)
        val equipment = equipmentService.findOneById(productId)

        if (equipmentService.isAlreadyBookmarkedByUser(userId = user.id, equipmentId = equipment.id)) {
            throw CommonException(ErrorCode.ALREADY_BOOKMARKED)
        }

        equipmentService.bookmarkProduct(user, equipment)
    }

    fun unBookmarkProduct(productId: Long) {
        val sessionId = SecurityContextHolder.getContext().authentication.name
        val user = userService.findBySessionId(sessionId)
        val equipment = equipmentService.findOneById(productId)
        val bookmarks = equipmentService.findBookmarkByUserAndEquipment(userId = user.id, equipmentId = equipment.id)

        if (bookmarks.isEmpty()) {
            throw CommonException(ErrorCode.NOT_BOOKMARKED)
        }

        equipmentService.unBookmarkProduct(bookmarks)
    }
}
