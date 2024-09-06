package com.wiyb.server.storage.database.entity.golf

import com.wiyb.server.storage.database.entity.common.BaseEntity
import com.wiyb.server.storage.database.entity.golf.dto.metric.BaseMetric
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity(name = "equipment_evaluated_metrics")
@Table(
    indexes = [
        Index(name = "idx_evaluated_average", columnList = "evaluated_average"),
        Index(name = "idx_forgiveness_average", columnList = "forgiveness_average"),
        Index(name = "idx_distance_average", columnList = "distance_average"),
        Index(name = "idx_accuracy_average", columnList = "accuracy_average"),
        Index(name = "idx_impact_feel_average", columnList = "impact_feel_average"),
        Index(name = "idx_impact_sound_average", columnList = "impact_sound_average"),
        Index(name = "idx_back_spin_average", columnList = "back_spin_average"),
        Index(name = "idx_distance_control_average", columnList = "distance_control_average"),
        Index(name = "idx_stiffness_average", columnList = "stiffness_average"),
        Index(name = "idx_weight_average", columnList = "weight_average"),
        Index(name = "idx_trajectory_average", columnList = "trajectory_average"),
        Index(name = "idx_touch_average", columnList = "touch_average"),
        Index(name = "idx_grip_comfort_average", columnList = "grip_comfort_average"),
        Index(name = "idx_durability_average", columnList = "durability_average")
    ]
)
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE equipment_evaluated_metrics SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
class EquipmentEvaluatedMetric(
    equipment: Equipment,
    ratingWeight: Float,
    forgiveness: Float? = 0f,
    distance: Float? = 0f,
    accuracy: Float? = 0f,
    impactFeel: Float? = 0f,
    impactSound: Float? = 0f,
    backspin: Float? = 0f,
    distanceControl: Float? = 0f,
    stiffness: Float? = 0f,
    weight: Float? = 0f,
    trajectory: Float? = 0f,
    touch: Float? = 0f,
    gripComfort: Float? = 0f,
    durability: Float? = 0f
) : BaseEntity(equipment.id) {
    // Columns

    @Column(name = "evaluated_count", nullable = false)
    var evaluatedCount: Long = 0
        protected set

    @Column(name = "evaluated_total", nullable = false)
    var evaluatedTotal: Float = 0f
        protected set

    @Column(name = "rating_weight", nullable = false)
    var ratingWeight: Float = ratingWeight
        protected set

    @Column(name = "forgiveness")
    var forgiveness: Float = forgiveness!!
        protected set

    @Column(name = "distance")
    var distance: Float = distance!!
        protected set

    @Column(name = "accuracy")
    var accuracy: Float = accuracy!!
        protected set

    @Column(name = "impact_feel")
    var impactFeel: Float = impactFeel!!
        protected set

    @Column(name = "impact_sound")
    var impactSound: Float = impactSound!!
        protected set

    @Column(name = "backspin")
    var backspin: Float = backspin!!
        protected set

    @Column(name = "distance_control")
    var distanceControl: Float = distanceControl!!
        protected set

    @Column(name = "stiffness")
    var stiffness: Float = stiffness!!
        protected set

    @Column(name = "weight")
    var weight: Float = weight!!
        protected set

    @Column(name = "trajectory")
    var trajectory: Float = trajectory!!
        protected set

    @Column(name = "touch")
    var touch: Float = touch!!
        protected set

    @Column(name = "grip_comfort")
    var gripComfort: Float = gripComfort!!
        protected set

    @Column(name = "durability")
    var durability: Float = durability!!
        protected set

    // Join Columns

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    var equipment: Equipment = equipment
        protected set

    // Virtual Columns

    @Column(name = "evaluated_average", insertable = false, updatable = false)
    var evaluatedAverage: Float = 0f

    @Column(name = "forgiveness_average", insertable = false, updatable = false)
    var forgivenessAverage: Float = 0f

    @Column(name = "distance_average", insertable = false, updatable = false)
    var distanceAverage: Float = 0f
        protected set

    @Column(name = "accuracy_average", insertable = false, updatable = false)
    var accuracyAverage: Float = 0f
        protected set

    @Column(name = "impact_feel_average", insertable = false, updatable = false)
    var impactFeelAverage: Float = 0f
        protected set

    @Column(name = "impact_sound_average", insertable = false, updatable = false)
    var impactSoundAverage: Float = 0f
        protected set

    @Column(name = "backspin_average", insertable = false, updatable = false)
    var backspinAverage: Float = 0f
        protected set

    @Column(name = "distance_control_average", insertable = false, updatable = false)
    var distanceControlAverage: Float = 0f
        protected set

    @Column(name = "stiffness_average", insertable = false, updatable = false)
    var stiffnessAverage: Float = 0f
        protected set

    @Column(name = "weight_average", insertable = false, updatable = false)
    var weightAverage: Float = 0f
        protected set

    @Column(name = "trajectory_average", insertable = false, updatable = false)
    var trajectoryAverage: Float = 0f
        protected set

    @Column(name = "touch_average", insertable = false, updatable = false)
    var touchAverage: Float = 0f
        protected set

    @Column(name = "grip_comfort_average", insertable = false, updatable = false)
    var gripComfortAverage: Float = 0f
        protected set

    @Column(name = "durability_average", insertable = false, updatable = false)
    var durabilityAverage: Float = 0f
        protected set

    fun addMetric(metric: BaseMetric) {
        evaluatedCount++
        evaluatedTotal += metric.sum()

        metric.forgiveness?.let { this.forgiveness += it }
        metric.distance?.let { this.distance += it }
        metric.accuracy?.let { this.accuracy += it }
        metric.impactFeel?.let { this.impactFeel += it }
        metric.impactSound?.let { this.impactSound += it }
        metric.backspin?.let { this.backspin += it }
        metric.distanceControl?.let { this.distanceControl += it }
        metric.stiffness?.let { this.stiffness += it }
        metric.weight?.let { this.weight += it }
        metric.trajectory?.let { this.trajectory += it }
        metric.touch?.let { this.touch += it }
        metric.gripComfort?.let { this.gripComfort += it }
        metric.durability?.let { this.durability += it }
    }
}
