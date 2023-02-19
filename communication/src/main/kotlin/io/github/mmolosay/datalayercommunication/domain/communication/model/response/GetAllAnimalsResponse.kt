package io.github.mmolosay.datalayercommunication.domain.communication.model.response

import io.github.mmolosay.datalayercommunication.domain.model.Animals
import io.github.mmolosay.datalayercommunication.domain.resource.Resource
import kotlinx.serialization.Serializable

@Serializable
data class GetAllAnimalsResponse(
    val resource: Resource<Animals>,
) : Response()