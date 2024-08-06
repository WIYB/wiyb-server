package com.wiyb.server.storage.cache.repository

import com.wiyb.server.storage.cache.entity.MostViewedProduct
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.ListCrudRepository

interface MostViewedProductRepository :
    CrudRepository<MostViewedProduct, Long>,
    ListCrudRepository<MostViewedProduct, Long>
