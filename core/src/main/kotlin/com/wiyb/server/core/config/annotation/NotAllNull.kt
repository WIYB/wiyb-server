package com.wiyb.server.core.config.annotation

import com.wiyb.server.core.domain.validation.NotAllNullValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NotAllNullValidator::class])
@MustBeDocumented
annotation class NotAllNull(
    val message: String = "All fields cannot be null",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
