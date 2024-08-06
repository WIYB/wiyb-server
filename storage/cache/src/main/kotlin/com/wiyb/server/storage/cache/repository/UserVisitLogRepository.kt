package com.wiyb.server.storage.cache.repository

import com.wiyb.server.storage.cache.entity.UserVisitLog
import org.springframework.data.repository.CrudRepository

interface UserVisitLogRepository : CrudRepository<UserVisitLog, Long>
