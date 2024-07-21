package com.wiyb.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServerApplication

fun main(args: Array<String>) {
    println("hello")
    runApplication<ServerApplication>(*args)
}
