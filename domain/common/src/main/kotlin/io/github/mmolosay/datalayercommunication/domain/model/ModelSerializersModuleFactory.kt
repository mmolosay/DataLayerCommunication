package io.github.mmolosay.datalayercommunication.domain.model

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object ModelSerializersModuleFactory {

    fun make(): SerializersModule =
        SerializersModule {
            polymorphic(Any::class) {
                subclass(Animal::class)
                subclass(Animals::class)
            }
        }
}