package com.wiyb.server.storage.cache.repository

import com.wiyb.server.storage.cache.entity.ProductViewCount
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.ListCrudRepository

interface ProductViewCountRepository :
    CrudRepository<ProductViewCount, Long>,
    ListCrudRepository<ProductViewCount, Long>
