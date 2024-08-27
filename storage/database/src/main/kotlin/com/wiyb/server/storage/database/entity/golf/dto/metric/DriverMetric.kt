package com.wiyb.server.storage.database.entity.golf.dto.metric

data class DriverMetric(
    override val forgiveness: Float,
    override val distance: Float,
    override val accuracy: Float,
    override val impactFeel: Float,
    override val impactSound: Float
) : BaseMetric() {
    override fun flatten(): List<Float> = listPad(forgiveness, distance, accuracy, impactFeel, impactSound)
}
