package io.github.mmolosay.datalayercommunication.data.communication

import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.DeleteAnimalByIdRequest
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.GetAllAnimalsRequest
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.Request
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.DeleteAnimalByIdResponse
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.GetAllAnimalsResponse
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.Response
import io.github.mmolosay.datalayercommunication.domain.communication.server.ResponseServer
import io.github.mmolosay.datalayercommunication.domain.resource.Resource
import io.github.mmolosay.datalayercommunication.domain.resource.getOrElse
import io.github.mmolosay.datalayercommunication.domain.resource.getOrNull

/**
 * Implementation of [ResponseServer], that employs repositories to obtain requested data.
 */
class RepositoryResponseServer(
    private val animalsRepository: AnimalsRepository,
) : ResponseServer {

    override suspend fun on(request: Request): Response =
        when (request) {
            is GetAllAnimalsRequest -> on(request)
            is DeleteAnimalByIdRequest -> on(request)
        }

    @Suppress("UNUSED_PARAMETER")
    private suspend fun on(request: GetAllAnimalsRequest): GetAllAnimalsResponse =
        animalsRepository
            .getAllAnimals()
            .run {
                GetAllAnimalsResponse(
                    data = getOrNull(),
                    failure = this as? Resource.Failure,
                )
            }

    private suspend fun on(request: DeleteAnimalByIdRequest): DeleteAnimalByIdResponse =
        DeleteAnimalByIdResponse(
            resource = animalsRepository.deleteAnimalById(request.animalId),
        )
}