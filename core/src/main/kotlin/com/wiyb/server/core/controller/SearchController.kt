package com.wiyb.server.core.controller

import com.wiyb.server.core.domain.product.IntegrateSearchDto
import com.wiyb.server.core.domain.product.SearchParameterDto
import com.wiyb.server.core.facade.SearchFacade
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/search")
class SearchController(
    private val searchFacade: SearchFacade
) {
    // todo: 쿼리스트링 validation 안됨
    @GetMapping
    fun integrateSearch(query: SearchParameterDto): ResponseEntity<IntegrateSearchDto> =
        ResponseEntity.ok().body(searchFacade.integrateSearch(query.keyword))
}
