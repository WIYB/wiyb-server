package com.wiyb.server.core.config.common

import com.wiyb.server.core.domain.common.CustomCookie
import com.wiyb.server.core.domain.common.CustomResponseCookie
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class PropertyInitializer(
    @Value("\${spring.config.origin.root-domain}")
    private val rootDomain: String
) {
    init {
        CustomCookie.rootDomain = rootDomain
        CustomResponseCookie.rootDomain = rootDomain
    }
}
