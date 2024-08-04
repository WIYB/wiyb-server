package com.wiyb.server.core.handler.product

import com.wiyb.server.core.service.ProductService
import com.wiyb.server.core.service.UserService
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.servlet.HandlerMapping

@Aspect
@Component
class ProductViewAspect(
    private val userService: UserService,
    private val productService: ProductService
) {
    @AfterReturning("execution(* com.wiyb.server.core.controller.ProductController.getProductDetail(..))")
    fun cacheProductViewCount() {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val sessionId: String = SecurityContextHolder.getContext().authentication.name
        val userId: Long = userService.findIdBySessionId(sessionId)
        val productId =
            (
                request.getAttribute(
                    HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE
                ) as Map<*, *>
            )["productId"].toString().toLong()

        productService.addUserVisitLog(userId, productId)
        productService.increaseProductViewCount(productId)
    }
}
