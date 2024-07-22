package com.wiyb.server.storage.entity.common

import io.hypersistence.tsid.TSID
import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.time.LocalDateTime
import java.util.Objects

val tsidFactory: TSID.Factory =
    TSID.Factory
        .builder()
        .withNodeBits(1)
        .build()

@MappedSuperclass
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE ? SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
abstract class BaseEntity : Persistable<Long> {
    @Id
    private val id: Long =
        tsidFactory
            .generate()
            .toLong()

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime? = LocalDateTime.now()
        protected set

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
        protected set

    @Transient
    private var isNew = true

    override fun getId(): Long = id

    override fun isNew(): Boolean = isNew

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is HibernateProxy && this::class != other::class) return false
        return id == getIdentifier(other)
    }

    override fun hashCode() = Objects.hashCode(id)

    private fun getIdentifier(obj: Any): Serializable =
        if (obj is HibernateProxy) {
            (
                {
                    obj.hibernateLazyInitializer.identifier
                }
            ) as Serializable
        } else {
            (obj as BaseEntity).id
        }

    @PostPersist
    @PostLoad
    protected fun load() {
        isNew = false
    }
}
