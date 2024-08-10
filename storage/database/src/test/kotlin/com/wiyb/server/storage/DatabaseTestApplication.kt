package com.wiyb.server.storage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class DatabaseTestApplication

fun main(args: Array<String>) {
    runApplication<DatabaseTestApplication>(*args)
}
