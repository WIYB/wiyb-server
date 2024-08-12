package com.wiyb.server.storage.database.repository.golf.detail

import com.wiyb.server.storage.database.entity.golf.detail.Putter
import com.wiyb.server.storage.database.repository.golf.detail.custom.PutterCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PutterRepository :
    JpaRepository<Putter, Long>,
    PutterCustomRepository
