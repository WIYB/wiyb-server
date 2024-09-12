package com.wiyb.server.core.integration.community

import com.wiyb.server.core.CoreContextTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostingTest : CoreContextTest() {
    @Test
    fun test() {
        println("hello")
    }
}
