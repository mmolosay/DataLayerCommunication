import io.github.mmolosay.datalayercommunication.data.communication.convertion.decode.SerializationRequestDecoder
import io.github.mmolosay.datalayercommunication.data.communication.convertion.decode.SerializationResponseDecoder
import io.github.mmolosay.datalayercommunication.data.communication.convertion.encode.SerializationRequestEncoder
import io.github.mmolosay.datalayercommunication.data.communication.convertion.encode.SerializationResponseEncoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.GetAllAnimalsRequest
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.DeleteAnimalByIdResponse
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.resource.Resource
import io.github.mmolosay.datalayercommunication.domain.resource.success
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beOfType
import org.junit.Test

class SerializationEncoderDecoderTests {

    @Test
    fun `encodes and decodes GetAllAnimalsRequest correctly`() {
        val encoder = SerializationRequestEncoder()
        val decoder = SerializationRequestDecoder()
        val request = GetAllAnimalsRequest

        val encoded = encoder.encode(request)
        val decoded = decoder.decode(encoded)

        decoded should beOfType<GetAllAnimalsRequest>()
        decoded shouldBe request
    }

    @Test
    fun `encodes and decodes DeleteAnimalByIdResponse correctly`() {
        val encoder = SerializationResponseEncoder()
        val decoder = SerializationResponseDecoder()
        val animal = Animal(id = 1536L, species = Animal.Species.Owl, name = "Phoebe", age = 2)
        val resource = Resource.success(animal)
        val response = DeleteAnimalByIdResponse(resource)

        val encoded = encoder.encode(response)
        val decoded = decoder.decode(encoded)

        decoded shouldBe response
    }
}