package io.github.mmolosay.datalayercommunication.communication.model.response

import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import kotlinx.serialization.Serializable

@Serializable
data class DeleteAnimalByIdResponse(
    val resource: Resource<Animal?>,
) : Response()