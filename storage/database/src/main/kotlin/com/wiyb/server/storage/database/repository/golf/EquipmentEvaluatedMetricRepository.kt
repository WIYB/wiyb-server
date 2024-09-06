package com.wiyb.server.storage.database.repository.golf

import com.wiyb.server.storage.database.entity.golf.EquipmentEvaluatedMetric
import org.springframework.data.jpa.repository.JpaRepository

interface EquipmentEvaluatedMetricRepository : JpaRepository<EquipmentEvaluatedMetric, Long>
