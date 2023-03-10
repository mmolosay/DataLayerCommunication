package io.github.mmolosay.datalayercommunication.communication.rpc.request

import kotlinx.serialization.Serializable

@Serializable
data class DeleteAnimalByIdRequest(
    val animalId: Long,
) : Request()