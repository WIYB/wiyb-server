package com.wiyb.server.core.domain.validation

import com.wiyb.server.core.config.annotation.NotAllNull
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class NotAllNullValidator : ConstraintValidator<NotAllNull, Any> {
    override fun isValid(
        value: Any?,
        context: ConstraintValidatorContext
    ): Boolean {
        if (value == null) {
            return true
        }

        val properties = value::class.memberProperties
        return properties.any { (it as? KProperty1<Any?, *>)?.get(value) != null }
    }
}
