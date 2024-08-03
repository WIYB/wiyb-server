package com.wiyb.server.storage.database.converter

interface CodedEnum<T> {
    fun getCode(): T
}
