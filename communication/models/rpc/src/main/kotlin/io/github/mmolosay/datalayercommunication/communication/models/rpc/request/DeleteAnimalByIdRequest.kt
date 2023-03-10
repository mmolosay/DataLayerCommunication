package io.github.mmolosay.datalayercommunication.communication.models.rpc.request

import kotlinx.serialization.Serializable

@Serializable
data class DeleteAnimalByIdRequest(
    val animalId: Long,
) : Request()