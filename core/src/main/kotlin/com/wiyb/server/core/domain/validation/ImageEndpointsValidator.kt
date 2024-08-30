package com.wiyb.server.core.domain.validation

import com.wiyb.server.core.config.annotation.ImageEndpoint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.factory.annotation.Value

class ImageEndpointsValidator(
    @Value("\${spring.config.origin.static}")
    private val staticOrigin: String
) : ConstraintValidator<ImageEndpoint, List<String>> {
    private val acceptedEndpoint = "^${staticOrigin.replace(".", "\\.")}/.*".toRegex()

    override fun isValid(
        value: List<String>?,
        context: ConstraintValidatorContext
    ): Boolean =
        if (value.isNullOrEmpty()) {
            true
        } else {
            value.find { !it.matches(acceptedEndpoint) } == null
        }
}
