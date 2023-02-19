package io.github.mmolosay.datalayercommunication.communication.model.response

import io.github.mmolosay.datalayercommunication.domain.model.Animals
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import kotlinx.serialization.Serializable

@Serializable
data class GetAllAnimalsResponse(
    val resource: Resource<Animals>,
) : Response()