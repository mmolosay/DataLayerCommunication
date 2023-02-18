package io.github.mmolosay.datalayercommunication.data.wearable

import io.github.mmolosay.datalayercommunication.domain.communication.CommunicationFailures.NoSuchNodeFailure
import io.github.mmolosay.datalayercommunication.domain.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.domain.communication.client.CommunicationClient
import io.github.mmolosay.datalayercommunication.domain.communication.model.Path
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.DeleteAnimalByIdRequest
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.GetAllAnimalsRequest
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.DeleteAnimalByIdResponse
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.GetAllAnimalsResponse
import io.github.mmolosay.datalayercommunication.domain.communication.model.toDestination
import io.github.mmolosay.datalayercommunication.domain.communication.singlePairedHandheldNode
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
            .singlePairedHandheldNode()
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
            .singlePairedHandheldNode()
            .getOrElse { return NoSuchNodeFailure }
            .toDestination(deleteAnimalByIdPath)
        val request = DeleteAnimalByIdRequest(animalId = id)
        return communicationClient
            .request<DeleteAnimalByIdResponse>(destination, request)
            .getOrElse { return it }
            .resource
    }
}