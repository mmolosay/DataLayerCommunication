package io.github.mmolosay.datalayercommunication.data.wearable

import io.github.mmolosay.datalayercommunication.communication.CommunicationClient
import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.firstHandheldNode
import io.github.mmolosay.datalayercommunication.communication.models.Path
import io.github.mmolosay.datalayercommunication.communication.models.toDestination
import io.github.mmolosay.datalayercommunication.communication.models.rpc.request.DeleteAnimalByIdRequest
import io.github.mmolosay.datalayercommunication.communication.models.rpc.request.GetAllAnimalsRequest
import io.github.mmolosay.datalayercommunication.communication.models.rpc.response.DeleteAnimalByIdResponse
import io.github.mmolosay.datalayercommunication.communication.models.rpc.response.GetAllAnimalsResponse
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
    private val requestsPath: Path,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : AnimalsRepository {

    override suspend fun getAllAnimals(): Resource<Animals> =
        withContext(dispatcher) block@{
            val destination = nodeProvider
                .firstHandheldNode()
                .getOrElse { return@block it }
                .toDestination(requestsPath)
            val request = GetAllAnimalsRequest
            communicationClient
                .request<GetAllAnimalsResponse>(destination, request)
                .getOrElse { return@block it }
                .resource
        }

    override suspend fun deleteAnimalById(id: Long): Resource<Animal?> =
        withContext(dispatcher) block@{
            val destination = nodeProvider
                .firstHandheldNode()
                .getOrElse { return@block it }
                .toDestination(requestsPath)
            val request = DeleteAnimalByIdRequest(animalId = id)
            communicationClient
                .request<DeleteAnimalByIdResponse>(destination, request)
                .getOrElse { return@block it }
                .resource
        }
}