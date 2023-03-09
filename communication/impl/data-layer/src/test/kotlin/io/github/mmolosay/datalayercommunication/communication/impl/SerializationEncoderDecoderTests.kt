package io.github.mmolosay.datalayercommunication.communication.impl

import io.github.mmolosay.datalayercommunication.communication.impl.convertion.decode.SerializationRequestDecoder
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.decode.SerializationResponseDecoder
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.encode.SerializationRequestEncoder
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.encode.SerializationResponseEncoder
import io.github.mmolosay.datalayercommunication.domain.models.Animal
import io.github.mmolosay.datalayercommunication.domain.models.ModelSerializersModuleFactory
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.success
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beOfType
import kotlinx.serialization.json.Json
import org.junit.Test

class SerializationEncoderDecoderTests {

    @Test
    fun `encodes and decodes GetAllAnimalsRequest correctly`() {
        val format = Json { serializersModule = ModelSerializersModuleFactory.make() }
        val encoder = SerializationRequestEncoder(format)
        val decoder = SerializationRequestDecoder(format)
        val request =
            io.github.mmolosay.datalayercommunication.communication.models.request.GetAllAnimalsRequest

        val encoded = encoder.encode(request)
        val decoded = decoder.decode(encoded)

        decoded should beOfType<io.github.mmolosay.datalayercommunication.communication.models.request.GetAllAnimalsRequest>()
        decoded shouldBe request
    }

    @Test
    fun `encodes and decodes DeleteAnimalByIdResponse correctly`() {
        val format = Json { serializersModule = ModelSerializersModuleFactory.make() }
        val encoder = SerializationResponseEncoder(format)
        val decoder = SerializationResponseDecoder(format)
        val animal = Animal(id = 1536L, species = Animal.Species.Owl, name = "Phoebe", age = 2)
        val resource = Resource.success(animal)
        val response =
            io.github.mmolosay.datalayercommunication.communication.models.response.DeleteAnimalByIdResponse(
                resource
            )

        val encoded = encoder.encode(response)
        val decoded = decoder.decode(encoded)

        decoded shouldBe response
    }
}