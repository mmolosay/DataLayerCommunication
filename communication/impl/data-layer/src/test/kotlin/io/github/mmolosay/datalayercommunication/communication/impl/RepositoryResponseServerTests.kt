package io.github.mmolosay.datalayercommunication.communication.impl

import io.github.mmolosay.datalayercommunication.communication.rpc.request.DeleteAnimalByIdRequest
import io.github.mmolosay.datalayercommunication.communication.rpc.request.GetAllAnimalsRequest
import io.github.mmolosay.datalayercommunication.communication.rpc.response.DeleteAnimalByIdResponse
import io.github.mmolosay.datalayercommunication.communication.rpc.response.GetAllAnimalsResponse
import io.github.mmolosay.datalayercommunication.domain.models.Animal
import io.github.mmolosay.datalayercommunication.domain.models.Animals
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.success
import io.kotest.matchers.should
import io.kotest.matchers.types.beOfType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryResponseServerTests {

    @Test
    fun `when GetAllAnimalsRequest returns GetAllAnimalsResponse`() = runTest {
        val animals = Animals(emptyList())
        val animalsRepository = mockk<AnimalsRepository>()
        coEvery { animalsRepository.getAllAnimals() } returns Resource.success(animals)
        val server = io.github.mmolosay.datalayercommunication.data.RepositoryResponseServer(
            animalsRepository
        )
        val request =
            GetAllAnimalsRequest

        val response = server.reciprocate(request)

        response should beOfType<GetAllAnimalsResponse>()
    }

    @Test
    fun `when DeleteAnimalByIdRequest returns DeleteAnimalByIdResponse`() = runTest {
        val animalsRepository = mockk<AnimalsRepository>()
        coEvery { animalsRepository.deleteAnimalById(any()) } returns Resource.success<Animal?>(null)
        val server = io.github.mmolosay.datalayercommunication.data.RepositoryResponseServer(
            animalsRepository
        )
        val request =
            DeleteAnimalByIdRequest(
                animalId = 0L
            )

        val response = server.reciprocate(request)

        response should beOfType<DeleteAnimalByIdResponse>()
    }
}