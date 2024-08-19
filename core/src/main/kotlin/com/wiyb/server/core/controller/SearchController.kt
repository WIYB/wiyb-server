package com.wiyb.server.core.controller

import com.wiyb.server.core.domain.search.SearchQueryDto
import com.wiyb.server.core.facade.ProductViewFacade
import com.wiyb.server.core.facade.SearchFacade
import com.wiyb.server.storage.database.entity.golf.dto.EquipmentSimpleDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchParameterDto
import com.wiyb.server.storage.database.entity.golf.dto.SearchResultDto
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
    @GetMapping("/product")
    fun productIntegrateSearch(
        @Valid query: SearchQueryDto
    ): ResponseEntity<SearchResultDto<EquipmentSimpleDto>> =
        ResponseEntity.ok().body(
            searchFacade.productIntegrateSearch(
                SearchParameterDto.fromQuery(
                    keyword = query.keyword,
                    filters = query.filters,
                    sort = query.sort,
                    contextId = query.contextId,
                    offset = query.offset,
                    size = query.size
                )
            )
        )

    @GetMapping("/product/popular")
    fun getPopularKeywords(): ResponseEntity<List<String>> = ResponseEntity.ok().body(emptyList())
//        ResponseEntity.ok().body(productViewFacade.getPopularProductNames())
}
