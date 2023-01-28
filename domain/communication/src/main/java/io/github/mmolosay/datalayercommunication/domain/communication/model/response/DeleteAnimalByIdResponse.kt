package io.github.mmolosay.datalayercommunication.domain.communication.model.response

import io.github.mmolosay.datalayercommunication.domain.model.Animal
import kotlinx.serialization.Serializable

@Serializable
class DeleteAnimalByIdResponse(
    val data: Animal?,
) : Response()