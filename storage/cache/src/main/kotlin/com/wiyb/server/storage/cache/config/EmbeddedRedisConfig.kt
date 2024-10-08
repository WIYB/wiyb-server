package com.wiyb.server.storage.cache.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer

@Profile("local", "test")
@Configuration()
class EmbeddedRedisConfig(
    @Value("\${spring.redis.port}")
    private val port: Int
) {
    private val server: RedisServer = RedisServer(port)

    @PostConstruct
    fun startRedis() {
        server.start()
    }

    @PreDestroy
    private fun stopRedis() {
        server.stop()
    }
}
