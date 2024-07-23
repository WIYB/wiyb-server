package com.wiyb.server.storage.converter

interface CodedEnum<T> {
    fun getCode(): T
}
