package com.wiyb.server.core.controller

import com.wiyb.server.core.CoreContextTest
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class SearchControllerTest(
    private val searchController: SearchController
) : CoreContextTest() {
    @Test
    fun productIntegrateSearch() {
        println("gogo")
    }
}
