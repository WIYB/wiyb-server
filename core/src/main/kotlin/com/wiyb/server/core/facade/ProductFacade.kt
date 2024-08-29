package com.wiyb.server.core.facade

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.core.domain.product.PostProductReviewDto
import com.wiyb.server.core.domain.product.ProductDetailDto
import com.wiyb.server.core.domain.product.mapper.ProductMapper
import com.wiyb.server.core.domain.search.mapper.SearchKeywordMapper
import com.wiyb.server.core.service.BrandService
import com.wiyb.server.core.service.EquipmentService
import com.wiyb.server.core.service.UserService
import com.wiyb.server.core.service.YoutubeService
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
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

    fun getProductReviews(productId: Long) = equipmentService.findReviewByEquipmentId(productId)

    fun getProductDetail(
        productId: Long,
        type: EquipmentType
    ): ProductDetailDto {
        val session = SecurityContextHolder.getContext().authentication
        val equipmentDto = equipmentService.findOneWithDetailById(productId, type)

        // todo: 결과 캐싱(일주일? 하루?) 및 캐싱된 결과가 있을 경우 캐싱된 결과 반환
        val youtubeResults = youtubeService.search(SearchKeywordMapper.to(equipmentDto))
        equipmentDto.reviews = equipmentService.findSimpleReviewByEquipmentId(productId)

        val productDetail = ProductMapper.to(equipmentDto, youtubeResults)

        // todo: Spring Security에서 static 메서드로 캡슐화
        if (session.principal != "anonymousUser" && !session.authorities.stream().anyMatch { it.authority.equals("ROLE_GUEST") }) {
            val user = userService.findBySessionId(session.name)
            val isBookmarked = equipmentService.isAlreadyBookmarkedByUser(userId = user.id, equipmentId = productId)
            productDetail.isBookmarked = isBookmarked
        }

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
