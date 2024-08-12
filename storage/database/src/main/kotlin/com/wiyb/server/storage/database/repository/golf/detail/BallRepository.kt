package com.wiyb.server.storage.database.repository.golf.detail

import com.wiyb.server.storage.database.entity.golf.detail.Ball
import com.wiyb.server.storage.database.repository.golf.detail.custom.BallCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BallRepository :
    JpaRepository<Ball, Long>,
    BallCustomRepository
