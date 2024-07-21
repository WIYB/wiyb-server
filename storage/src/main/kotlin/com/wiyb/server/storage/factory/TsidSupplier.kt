package com.wiyb.server.storage.factory

import io.hypersistence.tsid.TSID
import java.util.function.Supplier

internal class TsidSupplier : Supplier<TSID.Factory> {
    override fun get(): TSID.Factory =
        TSID.Factory
            .builder()
            .withNodeBits(1)
            .build()
}
