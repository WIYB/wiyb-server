package com.wiyb.server.core.config.annotation

import com.wiyb.server.core.domain.validation.NotBlankElementListValidator
import jakarta.validation.Constraint
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

@Target(FIELD)
@Retention(RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [NotBlankElementListValidator::class])
annotation class NotBlankElementList(
    val message: String = "List elements must not be blank",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<*>> = []
)
