package io.github.mmolosay.datalayercommunication.utils.resource

import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object ResourceSerialializersModuleFactory {

    fun make(): SerializersModule =
        SerializersModule {
            polymorphic(Resource::class) {
                subclass(Resource.Success.serializer(PolymorphicSerializer(Any::class)))
            }
        }
}