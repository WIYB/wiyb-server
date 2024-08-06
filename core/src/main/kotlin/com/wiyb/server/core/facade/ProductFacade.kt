package com.wiyb.server.core.facade

import com.wiyb.server.core.domain.exception.CommonException
import com.wiyb.server.core.domain.exception.ErrorCode
import com.wiyb.server.core.domain.product.PostProductReviewDto
import com.wiyb.server.core.service.EquipmentService
import com.wiyb.server.core.service.UserService
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class ProductFacade(
    private val userService: UserService,
    private val equipmentService: EquipmentService
) {
    fun getProductReviews(productId: Long) = equipmentService.findReviewByEquipmentId(productId)

    fun getProductDetail(productId: Long): EquipmentDto {
        val equipmentDto = equipmentService.findOneWithDetailById(productId)
        equipmentDto.reviews = equipmentService.findSimpleReviewByEquipmentId(productId)

        return equipmentDto
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
