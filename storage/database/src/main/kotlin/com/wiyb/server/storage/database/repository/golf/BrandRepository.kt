package com.wiyb.server.storage.database.repository.golf

import com.wiyb.server.storage.database.entity.golf.Brand
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository : JpaRepository<Brand, Long>
