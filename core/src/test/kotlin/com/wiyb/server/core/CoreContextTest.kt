package com.wiyb.server.core

import org.junit.jupiter.api.Tag
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@Tag("context")
@SpringBootTest
@ComponentScan(basePackages = ["com.wiyb.server.storage.database", "com.wiyb.server.storage.cache"])
abstract class CoreContextTest
