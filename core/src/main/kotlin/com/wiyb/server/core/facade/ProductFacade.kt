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
        val equipmentDto = equipmentService.findOneWithDetailById(productId, type)
        // todo: 결과 캐싱(일주일? 하루?) 및 캐싱된 결과가 있을 경우 캐싱된 결과 반환
        val youtubeResults = youtubeService.search(SearchKeywordMapper.to(equipmentDto))
        equipmentDto.reviews = equipmentService.findSimpleReviewByEquipmentId(productId)

        return ProductMapper.to(equipmentDto, youtubeResults)
    }

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

        equipmentService.postProductReview(dto.toEntity(user, equipment))

        // todo: 추후 기획 완성 시, 장비 별 metric 집계 로직 세분화
        if (dto.evaluationMetric != null) {
            equipment.addEvaluationMetric(dto.evaluationMetric)
            equipmentService.saveEquipment(equipment)
        }
    }
}
