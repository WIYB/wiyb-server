package com.wiyb.server.storage.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class DataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "storage.datasource")
    fun hikariConfig(): HikariConfig {
        return HikariConfig()
    }

    @Bean
    fun datasource(@Qualifier("hikariConfig") config: HikariConfig): HikariDataSource {
        return HikariDataSource(config)
    }
}