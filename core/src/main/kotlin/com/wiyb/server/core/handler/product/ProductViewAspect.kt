package com.wiyb.server.core.handler.product

import com.wiyb.server.core.domain.product.mapper.EquipmentCacheMapper
import com.wiyb.server.core.service.ProductService
import com.wiyb.server.core.service.UserService
import com.wiyb.server.storage.cache.entity.CachedProduct
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentDto
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Aspect
@Component
class ProductViewAspect(
    private val userService: UserService,
    private val productService: ProductService
) {
    @AfterReturning(
        pointcut = "execution(* com.wiyb.server.core.controller.ProductController.getProductDetail(..))",
        returning = "response"
    )
    fun cacheProductViewCount(response: ResponseEntity<EquipmentDto>) {
        val result = response.body ?: return

        val sessionId: String = SecurityContextHolder.getContext().authentication.name
        val productId = result.id.toLong()

        if (sessionId != "anonymousUser") {
            val userId: Long = userService.findIdBySessionId(sessionId)
            productService.addUserVisitLog(userId, productId)
        }

        val product: CachedProduct = productService.findById(productId) ?: EquipmentCacheMapper.to(result)

        productService.increaseViewCount(product)
    }
}
