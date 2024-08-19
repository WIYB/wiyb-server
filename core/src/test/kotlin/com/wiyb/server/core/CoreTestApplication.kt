package com.wiyb.server.core

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class CoreTestApplication

fun main(args: Array<String>) {
    runApplication<CoreTestApplication>(*args)
}
