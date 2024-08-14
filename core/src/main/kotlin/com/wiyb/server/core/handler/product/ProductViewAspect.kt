package com.wiyb.server.core.handler.product

import com.wiyb.server.core.domain.product.mapper.EquipmentCacheMapper
import com.wiyb.server.core.service.EquipmentService
import com.wiyb.server.core.service.ProductService
import com.wiyb.server.core.service.UserService
import com.wiyb.server.storage.cache.entity.CachedProduct
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
    private val productService: ProductService,
    private val equipmentService: EquipmentService
) {
    @AfterReturning("execution(* com.wiyb.server.core.controller.ProductController.getProductDetail(..))")
    fun cacheProductViewCount() {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val sessionId: String = SecurityContextHolder.getContext().authentication.name
        val productId =
            (
                request.getAttribute(
                    HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE
                ) as Map<*, *>
            )["productId"].toString().toLong()

        if (sessionId != "anonymousUser") {
            val userId: Long = userService.findIdBySessionId(sessionId)
            productService.addUserVisitLog(userId, productId)
        }

        val product: CachedProduct =
            if (!productService.existsById(productId)) {
                val equipment = equipmentService.findSimpleById(productId)
                EquipmentCacheMapper.fromSimpleDto(equipment)
            } else {
                productService.findById(productId)
            }
        productService.increaseViewCount(product)
    }
}
