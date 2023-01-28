package io.github.mmolosay.datalayercommunication.domain.communication.model.request

import kotlinx.serialization.Serializable

@Serializable
data class DeleteAnimalByIdRequest(
    val animalId: Long,
) : Request()