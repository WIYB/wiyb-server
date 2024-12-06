package com.wiyb.server.core.domain.validation

import com.wiyb.server.core.config.annotation.ImageEndpoint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.factory.annotation.Value

class ImageEndpointValidator(
    @Value("\${spring.config.origin.static}")
    private val staticOrigin: String
) : ConstraintValidator<ImageEndpoint, String> {
    private val acceptedEndpoint = "^${staticOrigin.replace(".", "\\.")}/.*".toRegex()

    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext
    ): Boolean =
        if (value.isNullOrEmpty()) {
            true
        } else {
            value.matches(acceptedEndpoint)
        }
}
