package com.wiyb.server.core.config.annotation

import com.wiyb.server.core.domain.validation.EnumValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

@Target(FIELD)
@Retention(RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [EnumValidator::class])
annotation class ValueOfEnum(
    val enumClass: KClass<out Enum<*>>,
    val excludes: Array<String> = [],
    val message: String = "",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
