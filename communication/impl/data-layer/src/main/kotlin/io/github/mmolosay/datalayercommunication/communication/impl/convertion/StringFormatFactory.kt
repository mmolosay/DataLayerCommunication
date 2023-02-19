package io.github.mmolosay.datalayercommunication.communication.impl.convertion

import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus

object StringFormatFactory {

    // https://github.com/Kotlin/kotlinx.serialization/issues/2190
    fun of(
        vararg modules: SerializersModule,
    ): StringFormat =
        Json { serializersModule = modules.fold(EmptySerializersModule()) { acc, module -> acc + module } }
}