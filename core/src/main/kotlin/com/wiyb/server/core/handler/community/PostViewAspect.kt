package com.wiyb.server.core.handler.community

import com.wiyb.server.core.domain.community.PostDetailDto
import com.wiyb.server.core.service.CommunityService
import org.aspectj.lang.annotation.Aspect
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Aspect
@Component
class PostViewAspect(
    private val communityService: CommunityService
) {
//    @AfterReturning(
//        pointcut = "execution(* com.wiyb.server.core.controller.CommunityController.getPostDetail(..))",
//        returning = "response"
//    )
    fun hitPostView(response: ResponseEntity<PostDetailDto>) {
        val result = response.body ?: return

        communityService.increaseCommentCount(result.id.toLong())
    }
}
