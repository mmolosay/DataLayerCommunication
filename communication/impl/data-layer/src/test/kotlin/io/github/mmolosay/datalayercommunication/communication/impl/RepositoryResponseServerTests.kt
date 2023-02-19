package io.github.mmolosay.datalayercommunication.communication.impl

import io.github.mmolosay.datalayercommunication.communication.impl.RepositoryResponseServer
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.DeleteAnimalByIdRequest
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.GetAllAnimalsRequest
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.DeleteAnimalByIdResponse
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.GetAllAnimalsResponse
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import io.github.mmolosay.datalayercommunication.domain.resource.Resource
import io.github.mmolosay.datalayercommunication.domain.resource.success
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
        val animalsRepository = mockk<AnimalsRepository>()
        coEvery { animalsRepository.getAllAnimals() } returns Resource.success(emptyList())
        val server = RepositoryResponseServer(animalsRepository)
        val request = GetAllAnimalsRequest

        val response = server.reciprocate(request)

        response should beOfType<GetAllAnimalsResponse>()
    }

    @Test
    fun `when DeleteAnimalByIdRequest returns DeleteAnimalByIdResponse`() = runTest {
        val animalsRepository = mockk<AnimalsRepository>()
        coEvery { animalsRepository.deleteAnimalById(any()) } returns Resource.success<Animal?>(null)
        val server = RepositoryResponseServer(animalsRepository)
        val request = DeleteAnimalByIdRequest(animalId = 0L)

        val response = server.reciprocate(request)

        response should beOfType<DeleteAnimalByIdResponse>()
    }
}