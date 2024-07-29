package com.wiyb.server.core.domain.validation

import com.wiyb.server.core.config.annotation.ValueOfEnum
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class EnumValidator : ConstraintValidator<ValueOfEnum, CharSequence> {
    private val acceptedValues: MutableList<String> = mutableListOf()

    override fun initialize(constraintAnnotation: ValueOfEnum) {
        super.initialize(constraintAnnotation)

        acceptedValues.addAll(
            constraintAnnotation.enumClass.java
                .enumConstants
                .map { it.name }
        )
    }

    override fun isValid(
        value: CharSequence?,
        context: ConstraintValidatorContext
    ): Boolean =
        if (value == null) {
            true
        } else {
            acceptedValues.contains(value.toString().uppercase())
        }
}
