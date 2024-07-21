package com.wiyb.server.storage.repository

import com.wiyb.server.storage.entity.ExampleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ExampleRepository : JpaRepository<ExampleEntity, Long>
