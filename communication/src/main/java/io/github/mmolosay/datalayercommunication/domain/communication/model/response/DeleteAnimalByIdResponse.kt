package io.github.mmolosay.datalayercommunication.domain.communication.model.response

import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.resource.Resource
import kotlinx.serialization.Serializable

@Serializable
data class DeleteAnimalByIdResponse(
    val resource: Resource<Animal?>,
) : Response()