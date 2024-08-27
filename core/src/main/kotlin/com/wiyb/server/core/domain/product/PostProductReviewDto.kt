package com.wiyb.server.core.domain.product

import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.EquipmentReview
import com.wiyb.server.storage.database.entity.user.User
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length

data class PostProductReviewDto(
    @field:Length(min = 10, max = 300, message = "content length must be between 10 and 300")
    @field:NotBlank
    val content: String,
    val imageUrls: List<String>? = null,
    @field:NotNull(message = "evaluation metric must be provided")
    @field:Valid
    val evaluationMetric: EvaluationMetricDto
) {
    fun toEntity(
        user: User,
        equipment: Equipment
    ) = EquipmentReview(
        user = user,
        equipment = equipment,
        content = content,
        imageUrls = imageUrls,
        evaluationMetric = evaluationMetric.flatten(equipment.type)
    )
}
