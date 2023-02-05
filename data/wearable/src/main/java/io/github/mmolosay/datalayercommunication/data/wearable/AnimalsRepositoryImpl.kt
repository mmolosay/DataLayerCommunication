package io.github.mmolosay.datalayercommunication.data.wearable

import io.github.mmolosay.datalayercommunication.domain.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.domain.communication.client.CommunicationClient
import io.github.mmolosay.datalayercommunication.domain.communication.model.Path
import io.github.mmolosay.datalayercommunication.domain.communication.model.node.toDestination
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.DeleteAnimalByIdRequest
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.GetAllAnimalsRequest
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.DeleteAnimalByIdResponse
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.GetAllAnimalsResponse
import io.github.mmolosay.datalayercommunication.domain.communication.filterPairedToThis
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository

class AnimalsRepositoryImpl(
    private val nodeProvider: NodeProvider,
    private val communicationClient: CommunicationClient,
    private val getAllAnimalsPath: Path,
    private val deleteAnimalByIdPath: Path,
) : AnimalsRepository {

    override suspend fun getAllAnimals(): List<Animal> {
        val destination = nodeProvider
            .handheld()
            .filterPairedToThis()
            .singleOrNull()
            ?.node
            ?.toDestination(getAllAnimalsPath)
            ?: return emptyList() // TODO: error handling
        val request = GetAllAnimalsRequest
        return communicationClient
            .request<GetAllAnimalsResponse>(destination, request)
            .getOrThrow()
            .data
    }

    override suspend fun deleteAnimalById(id: Long): Animal? {
        val destination = nodeProvider
            .handheld()
            .filterPairedToThis()
            .singleOrNull()
            ?.node
            ?.toDestination(deleteAnimalByIdPath)
            ?: return null // TODO: error handling
        val request = DeleteAnimalByIdRequest(animalId = id)
        return communicationClient
            .request<DeleteAnimalByIdResponse>(destination, request)
            .getOrThrow()
            .data
    }
}