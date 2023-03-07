package io.github.mmolosay.datalayercommunication.data.wearable

import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.CommunicationClient
import io.github.mmolosay.datalayercommunication.communication.failures.ConnectionFailure
import io.github.mmolosay.datalayercommunication.communication.model.Path
import io.github.mmolosay.datalayercommunication.communication.model.toDestination
import io.github.mmolosay.datalayercommunication.communication.models.request.DeleteAnimalByIdRequest
import io.github.mmolosay.datalayercommunication.communication.models.request.GetAllAnimalsRequest
import io.github.mmolosay.datalayercommunication.communication.models.response.DeleteAnimalByIdResponse
import io.github.mmolosay.datalayercommunication.communication.models.response.GetAllAnimalsResponse
import io.github.mmolosay.datalayercommunication.communication.singleConnectedHandheldNode
import io.github.mmolosay.datalayercommunication.domain.models.Animal
import io.github.mmolosay.datalayercommunication.domain.models.Animals
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.getOrElse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimalsRepositoryImpl(
    private val nodeProvider: NodeProvider,
    private val communicationClient: CommunicationClient,
    private val getAllAnimalsPath: Path,
    private val deleteAnimalByIdPath: Path,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : AnimalsRepository {

    override suspend fun getAllAnimals(): Resource<Animals> =
        withContext(dispatcher) block@{
            val destination = nodeProvider
                .singleConnectedHandheldNode()
                .getOrElse { return@block ConnectionFailure }
                .toDestination(getAllAnimalsPath)
            val request = GetAllAnimalsRequest
            communicationClient
                .request<GetAllAnimalsResponse>(destination, request)
                .getOrElse { return@block it }
                .resource
        }

    override suspend fun deleteAnimalById(id: Long): Resource<Animal?> =
        withContext(dispatcher) block@{
            val destination = nodeProvider
                .singleConnectedHandheldNode()
                .getOrElse { return@block ConnectionFailure }
                .toDestination(deleteAnimalByIdPath)
            val request = DeleteAnimalByIdRequest(animalId = id)
            communicationClient
                .request<DeleteAnimalByIdResponse>(destination, request)
                .getOrElse { return@block it }
                .resource
        }
}