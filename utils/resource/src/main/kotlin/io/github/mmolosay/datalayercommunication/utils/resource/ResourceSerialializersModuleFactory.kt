package io.github.mmolosay.datalayercommunication.utils.resource

import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

// https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/polymorphism.md#polymorphism-and-generic-classes
object ResourceSerialializersModuleFactory {

    fun make(): SerializersModule =
        SerializersModule {
            polymorphic(Resource::class) {
                subclass(Resource.Success.serializer(PolymorphicSerializer(Any::class)))
            }
        }
}