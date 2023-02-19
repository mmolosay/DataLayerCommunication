package io.github.mmolosay.datalayercommunication.communication.impl

import io.github.mmolosay.datalayercommunication.communication.model.request.DeleteAnimalByIdRequest
import io.github.mmolosay.datalayercommunication.communication.model.request.GetAllAnimalsRequest
import io.github.mmolosay.datalayercommunication.communication.model.request.Request
import io.github.mmolosay.datalayercommunication.communication.model.response.DeleteAnimalByIdResponse
import io.github.mmolosay.datalayercommunication.communication.model.response.GetAllAnimalsResponse
import io.github.mmolosay.datalayercommunication.communication.model.response.Response
import io.github.mmolosay.datalayercommunication.communication.server.ResponseServer
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository

/**
 * Implementation of [ResponseServer], that employs repositories to obtain requested data.
 */
class RepositoryResponseServer(
    private val animalsRepository: AnimalsRepository,
) : ResponseServer {

    override suspend fun reciprocate(request: Request): Response =
        when (request) {
            is GetAllAnimalsRequest -> on(request)
            is DeleteAnimalByIdRequest -> on(request)
        }

    @Suppress("UNUSED_PARAMETER")
    private suspend fun on(request: GetAllAnimalsRequest): GetAllAnimalsResponse =
        animalsRepository
            .getAllAnimals()
            .let { GetAllAnimalsResponse(it) }

    private suspend fun on(request: DeleteAnimalByIdRequest): DeleteAnimalByIdResponse =
        animalsRepository
            .deleteAnimalById(request.animalId)
            .let { DeleteAnimalByIdResponse(it) }
}