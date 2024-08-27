package com.wiyb.server.storage.database.entity.golf.dto.metric

interface EvaluatedMetric {
    // 얼마나 샷의 미스를 잡아주는가 (관용성)
    val forgiveness: Float?

    // 얼마나 먼 거리를 보내는가 (비거리)
    val distance: Float?

    // 얼마나 원하는 곳으로 보낼 수 있는가 (정확도)
    val accuracy: Float?

    // 얼마나 맞는 느낌이 좋은가 (타감)
    val impactFeel: Float?

    // 공이 맞는 소리는 좋은가 (타구음)
    val impactSound: Float?

    // 어느정도의 백스핀을 만들어 주는가 (백스핀)
    val backSpin: Float?

    // 거리를 쉽게 예측할 수 있는가 (거리감)
    val distanceControl: Float?

    // 얼마나 강한가 샤프트 휨 (강성)
    val stiffness: Float?

    // 얼마나 무거운가 (무게)
    val weight: Float?

    // 공이 높게 뜨는 정도 (탄도)
    val trajectory: Float?

    // 소프트한가 하드한가 (터치감)
    val touch: Float?

    // 땀이 많은 사람도 그립을 잡기 좋은가 (그립력)
    val gripComfort: Float?

    // 어느정도 오래 쓸 수 있는가 (내구성)
    val durability: Float?
}
