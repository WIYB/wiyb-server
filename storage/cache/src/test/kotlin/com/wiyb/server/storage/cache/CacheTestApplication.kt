package com.wiyb.server.storage.cache

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class CacheTestApplication

fun main(args: Array<String>) {
    runApplication<CacheTestApplication>(*args)
}
