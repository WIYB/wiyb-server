package com.wiyb.server.core.domain.product

import com.wiyb.server.core.config.annotation.ImageEndpoint
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.EquipmentReview
import com.wiyb.server.storage.database.entity.user.User
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Length

data class PostProductReviewDto(
    @field:NotNull(message = "evaluation metric must be provided")
    @field:Valid
    val evaluationMetric: EvaluationMetricDto,
    @field:Length(max = 300, message = "content must be less than 300 characters")
    val content: String? = null,
    @field:Size(max = 5, message = "image urls must be less than 5")
    @field:ImageEndpoint
    val imageUrls: List<String>? = null
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
