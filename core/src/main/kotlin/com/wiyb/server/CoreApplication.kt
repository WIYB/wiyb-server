package com.wiyb.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling // todo: 스케줄러 서버 분리 시 제거
@ConfigurationPropertiesScan
@SpringBootApplication
class CoreApplication

fun main() {
    runApplication<CoreApplication>()
}
