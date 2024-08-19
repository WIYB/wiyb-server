package com.wiyb.server.core.controller

import com.wiyb.server.core.domain.product.IntegrateSearchDto
import com.wiyb.server.core.domain.search.SearchQueryDto
import com.wiyb.server.core.facade.ProductViewFacade
import com.wiyb.server.core.facade.SearchFacade
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDto
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/search")
class SearchController(
    private val searchFacade: SearchFacade,
    private val productViewFacade: ProductViewFacade
) {
    @GetMapping
    fun integrateSearch(
        @Valid query: SearchQueryDto
    ): ResponseEntity<IntegrateSearchDto> =
        ResponseEntity.ok().body(
            searchFacade.integrateSearch(
                SearchParameterDto.fromQuery(
                    keyword = query.keyword,
                    filters = query.filters,
                    sort = query.sort,
                    sessionId = query.sessionId,
                    page = query.page,
                    size = query.pageSize
                )
            )
        )

    @GetMapping("/popular")
    fun getPopularKeywords(): ResponseEntity<List<String>> = ResponseEntity.ok().body(emptyList())
//        ResponseEntity.ok().body(productViewFacade.getPopularProductNames())
}
