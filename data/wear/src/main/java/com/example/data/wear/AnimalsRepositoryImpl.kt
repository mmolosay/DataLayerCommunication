package com.example.data.wear

import com.example.domain.Animal
import com.example.domain.AnimalsRepository
import com.example.domain.communication.CommunicationClient
import com.example.domain.communication.NodeProvider
import com.example.domain.communication.model.request.DeleteAnimalByIdRequest
import com.example.domain.communication.model.request.GetAllAnimalsRequest
import com.example.domain.communication.model.response.DeleteAnimalByIdResponse
import com.example.domain.communication.model.response.GetAllAnimalsResponse
import com.example.domain.communication.model.toDestination

class AnimalsRepositoryImpl(
    private val nodeProvider: NodeProvider,
    private val communicationClient: CommunicationClient,
) : AnimalsRepository {

    override suspend fun getAllAnimals(): List<Animal> {
        val destination = nodeProvider
            .mobile()
            .firstOrNull()
            ?.toDestination("/get-all-animals") // TODO: provide path?
            ?: return emptyList() // TODO: error handling
        val request = GetAllAnimalsRequest
        return communicationClient
            .request<GetAllAnimalsResponse>(destination, request)
            .getOrThrow()
            .data
    }

    override suspend fun deleteAnimalById(id: Long): Animal? {
        val destination = nodeProvider
            .mobile()
            .firstOrNull()
            ?.toDestination("/delete-random-animal-by-id")
            ?: return null
        val request = DeleteAnimalByIdRequest(animalId = id)
        return communicationClient
            .request<DeleteAnimalByIdResponse>(destination, request)
            .getOrThrow()
            .data
    }
}