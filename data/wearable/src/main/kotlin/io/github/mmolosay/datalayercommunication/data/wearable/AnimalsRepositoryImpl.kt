package io.github.mmolosay.datalayercommunication.data.wearable

import io.github.mmolosay.datalayercommunication.communication.failures.CommunicationFailures.NoSuchNodeFailure
import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.client.CommunicationClient
import io.github.mmolosay.datalayercommunication.communication.model.Path
import io.github.mmolosay.datalayercommunication.communication.model.request.DeleteAnimalByIdRequest
import io.github.mmolosay.datalayercommunication.communication.model.request.GetAllAnimalsRequest
import io.github.mmolosay.datalayercommunication.communication.model.response.DeleteAnimalByIdResponse
import io.github.mmolosay.datalayercommunication.communication.model.response.GetAllAnimalsResponse
import io.github.mmolosay.datalayercommunication.communication.model.toDestination
import io.github.mmolosay.datalayercommunication.communication.singleConnectedHandheldNode
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.model.Animals
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.getOrElse

class AnimalsRepositoryImpl(
    private val nodeProvider: NodeProvider,
    private val communicationClient: CommunicationClient,
    private val getAllAnimalsPath: Path,
    private val deleteAnimalByIdPath: Path,
) : AnimalsRepository {

    override suspend fun getAllAnimals(): Resource<Animals> {
        val destination = nodeProvider
            .singleConnectedHandheldNode()
            .getOrElse { return NoSuchNodeFailure }
            .toDestination(getAllAnimalsPath)
        val request = GetAllAnimalsRequest
        return communicationClient
            .request<GetAllAnimalsResponse>(destination, request)
            .getOrElse { return it }
            .resource
    }

    override suspend fun deleteAnimalById(id: Long): Resource<Animal?> {
        val destination = nodeProvider
            .singleConnectedHandheldNode()
            .getOrElse { return NoSuchNodeFailure }
            .toDestination(deleteAnimalByIdPath)
        val request = DeleteAnimalByIdRequest(animalId = id)
        return communicationClient
            .request<DeleteAnimalByIdResponse>(destination, request)
            .getOrElse { return it }
            .resource
    }
}