package com.wiyb.server.core.config.annotation

import com.wiyb.server.core.domain.validation.ImageEndpointValidator
import com.wiyb.server.core.domain.validation.ImageEndpointsValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

@Target(FIELD)
@Retention(RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [ImageEndpointValidator::class, ImageEndpointsValidator::class])
annotation class ImageEndpoint(
    val message: String = "Invalid image endpoint",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
