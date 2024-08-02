package com.wiyb.server.storage.repository.custom.impl

import com.wiyb.server.storage.entity.golf.Brand
import com.wiyb.server.storage.entity.golf.QBrand.brand
import com.wiyb.server.storage.entity.golf.QClubGrip.clubGrip
import com.wiyb.server.storage.entity.golf.QClubHead.clubHead
import com.wiyb.server.storage.entity.golf.QClubShaft.clubShaft
import com.wiyb.server.storage.entity.golf.QGolfBall.golfBall
import com.wiyb.server.storage.entity.golf.QGolfOtherEquipment.golfOtherEquipment
import com.wiyb.server.storage.repository.custom.BrandCustomRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class BrandCustomRepositoryImpl :
    QuerydslRepositorySupport(Brand::class.java),
    BrandCustomRepository {
    fun findByEquipmentNameKeyword(keyword: String): List<Brand> {
        val query =
            from(brand)
                .select()
                .select(brand)
                .leftJoin(brand.mutableClubHeads, clubHead)
                .fetchJoin()
                .leftJoin(brand.mutableClubShafts, clubShaft)
                .fetchJoin()
                .leftJoin(brand.mutableClubGrips, clubGrip)
                .fetchJoin()
                .leftJoin(brand.mutableGolfBalls, golfBall)
                .fetchJoin()
                .leftJoin(brand.mutableGolfOtherEquipments, golfOtherEquipment)
                .fetchJoin()
                .where(
                    clubHead.name
                        .containsIgnoreCase(keyword)
                        .or(clubShaft.name.containsIgnoreCase(keyword))
                        .or(clubGrip.name.containsIgnoreCase(keyword))
                        .or(golfBall.name.containsIgnoreCase(keyword))
                        .or(golfOtherEquipment.name.containsIgnoreCase(keyword))
                )
        return query.fetch()
    }
}
