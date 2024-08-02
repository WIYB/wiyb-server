package com.wiyb.server.storage.repository.golf

import com.wiyb.server.storage.entity.golf.Brand
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository : JpaRepository<Brand, Long>
