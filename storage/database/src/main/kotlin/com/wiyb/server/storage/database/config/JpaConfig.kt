package com.wiyb.server.storage.database.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = ["com.wiyb.server.storage.database.entity"])
@EnableJpaRepositories(basePackages = ["com.wiyb.server.storage.database.repository"])
internal class JpaConfig
