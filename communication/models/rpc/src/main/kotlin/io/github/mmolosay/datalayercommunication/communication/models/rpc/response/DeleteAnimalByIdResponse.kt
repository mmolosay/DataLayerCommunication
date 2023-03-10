package io.github.mmolosay.datalayercommunication.communication.models.rpc.response

import io.github.mmolosay.datalayercommunication.domain.models.Animal
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import kotlinx.serialization.Serializable

@Serializable
data class DeleteAnimalByIdResponse(
    val resource: Resource<Animal?>,
) : Response()