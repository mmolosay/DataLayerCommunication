package com.example.data.communication

import com.example.domain.AnimalsRepository
import com.example.domain.communication.model.request.DeleteAnimalByIdRequest
import com.example.domain.communication.model.request.GetAllAnimalsRequest
import com.example.domain.communication.model.request.Request
import com.example.domain.communication.model.response.DeleteAnimalByIdResponse
import com.example.domain.communication.model.response.GetAllAnimalsResponse
import com.example.domain.communication.model.response.Response
import com.example.domain.communication.server.ResponseServer

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
        GetAllAnimalsResponse(
            data = animalsRepository.getAllAnimals(),
        )

    private suspend fun on(request: DeleteAnimalByIdRequest): DeleteAnimalByIdResponse =
        DeleteAnimalByIdResponse(
            data = animalsRepository.deleteAnimalById(request.animalId),
        )
}