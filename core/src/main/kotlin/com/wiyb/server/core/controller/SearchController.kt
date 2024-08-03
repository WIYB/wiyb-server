package com.wiyb.server.core.controller

import com.wiyb.server.core.domain.golf.IntegrateSearchDto
import com.wiyb.server.core.domain.golf.SearchParameterDto
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
    @GetMapping
    fun integrateSearch(query: SearchParameterDto): ResponseEntity<IntegrateSearchDto> =
        ResponseEntity.ok().body(searchFacade.integrateSearch(query.keyword))
}
