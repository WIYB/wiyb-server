package com.wiyb.server.storage.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class ExampleEntity(
    @Column
    val exampleColumn: String
) : BaseEntity()
