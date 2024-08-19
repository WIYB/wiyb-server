package com.wiyb.server.core.domain.validation

import com.wiyb.server.core.config.annotation.SearchFilterKeywords
import com.wiyb.server.storage.database.entity.golf.constant.BrandName
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class SearchFilterValidator : ConstraintValidator<SearchFilterKeywords, CharSequence> {
    private val acceptedValues: MutableList<String> = mutableListOf()

    override fun initialize(constraintAnnotation: SearchFilterKeywords) {
        super.initialize(constraintAnnotation)

        acceptedValues.addAll(
            BrandName.entries.map { it.getCode().uppercase() } +
                EquipmentType.entries.map { it.getCode().uppercase() }
        )
    }

    override fun isValid(
        value: CharSequence?,
        context: ConstraintValidatorContext
    ): Boolean =
        if (value.isNullOrBlank()) {
            true
        } else {
            acceptedValues.containsAll(value.split(',').map { it.uppercase() })
        }
}
