package com.wiyb.server.storage.database.entity.golf.dto.metric

data class ShaftMetric(
    override val stiffness: Float,
    override val weight: Float,
    override val trajectory: Float
) : BaseMetric() {
    override fun flatten(): List<Float> = listPad(stiffness, weight, trajectory)
}
