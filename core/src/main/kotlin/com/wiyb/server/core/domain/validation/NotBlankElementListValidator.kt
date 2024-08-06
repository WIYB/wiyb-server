package com.wiyb.server.core.domain.validation

import com.wiyb.server.core.config.annotation.NotBlankElementList
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class NotBlankElementListValidator : ConstraintValidator<NotBlankElementList, List<*>> {
    override fun isValid(
        lists: List<*>?,
        context: ConstraintValidatorContext?
    ): Boolean = lists?.isNotEmpty() ?: false
}
