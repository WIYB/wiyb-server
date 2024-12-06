package com.wiyb.server.core.handler.search

import com.wiyb.server.core.service.SearchService
import com.wiyb.server.storage.database.entity.common.dto.PaginationResultDto
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.net.URLDecoder

@Aspect
@Component
class SearchKeywordAspect(
    private val searchService: SearchService
) {
    @AfterReturning(
        pointcut = "execution(* com.wiyb.server.core.controller.SearchController.productIntegrateSearch(..))",
        returning = "response"
    )
    fun hitSearchKeyword(response: ResponseEntity<PaginationResultDto<EquipmentSimpleDto>>) {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val result = response.body ?: return
        val keyword = splitQuery(request.queryString)["keyword"] ?: return

        if (result.metadata.isEmpty) return

        searchService.increaseHitCount(keyword)
    }

    fun splitQuery(query: String): Map<String, String> {
        val queryPairs: MutableMap<String, String> = LinkedHashMap()
        val pairs = query.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (pair in pairs) {
            val idx = pair.indexOf("=")
            queryPairs[URLDecoder.decode(pair.substring(0, idx), "UTF-8")] =
                URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
        }

        return queryPairs
    }
}
