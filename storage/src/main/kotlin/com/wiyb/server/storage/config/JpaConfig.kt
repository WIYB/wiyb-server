package com.wiyb.server.storage.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = ["com.wiyb.server.storage.entity"])
@EnableJpaRepositories(basePackages = ["com.wiyb.server.storage.repository"])
internal class JpaConfig
