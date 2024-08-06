package com.wiyb.server.storage.cache.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableRedisRepositories(basePackages = ["com.wiyb.server.storage.cache.repository"])
class RedisRepositoryConfig(
    @Value("\${spring.redis.host}")
    private val host: String,
    @Value("\${spring.redis.port}")
    private val port: Int
) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory = LettuceConnectionFactory(host, port)

    @Bean
    fun redisTemplate(): RedisTemplate<ByteArray, ByteArray> {
        val template = RedisTemplate<ByteArray, ByteArray>()
        template.connectionFactory = redisConnectionFactory()
        // todo: redis transaction 설정
        // https://sabarada.tistory.com/178
//        template.setEnableTransactionSupport(true)
        return template
    }
}
