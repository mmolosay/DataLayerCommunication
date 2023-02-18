package io.github.mmolosay.datalayercommunication.data.wearable

import io.github.mmolosay.datalayercommunication.domain.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.domain.communication.client.CommunicationClient
import io.github.mmolosay.datalayercommunication.domain.communication.filterPairedToThis
import io.github.mmolosay.datalayercommunication.domain.communication.model.Path
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.DeleteAnimalByIdRequest
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.GetAllAnimalsRequest
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.DeleteAnimalByIdResponse
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.GetAllAnimalsResponse
import io.github.mmolosay.datalayercommunication.domain.communication.model.toDestination
import io.github.mmolosay.datalayercommunication.domain.communication.resourceSingle
import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.model.Animals
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import io.github.mmolosay.datalayercommunication.domain.resource.Resource
import io.github.mmolosay.datalayercommunication.domain.resource.getOrElse

class AnimalsRepositoryImpl(
    private val nodeProvider: NodeProvider,
    private val communicationClient: CommunicationClient,
    private val getAllAnimalsPath: Path,
    private val deleteAnimalByIdPath: Path,
) : AnimalsRepository {

    override suspend fun getAllAnimals(): Resource<Animals> {
        val destination = nodeProvider
            .handheld()// TODO: redesign NodeProvider to return Resource
            .filterPairedToThis()
            .resourceSingle()
            .getOrElse { return it }
            .toDestination(getAllAnimalsPath)
        val request = GetAllAnimalsRequest
        return communicationClient
            .request<GetAllAnimalsResponse>(destination, request)
            .getOrThrow()
            .resource
    }

    override suspend fun deleteAnimalById(id: Long): Resource<Animal?> {
        val destination = nodeProvider
            .handheld()
            .filterPairedToThis()
            .resourceSingle()
            .getOrElse { return it }
            .toDestination(deleteAnimalByIdPath)
        val request = DeleteAnimalByIdRequest(animalId = id)
        return communicationClient
            .request<DeleteAnimalByIdResponse>(destination, request)
            .getOrThrow()
            .resource
    }
}