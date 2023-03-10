package io.github.mmolosay.datalayercommunication.communication.models.rpc.response

import io.github.mmolosay.datalayercommunication.domain.models.Animals
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import kotlinx.serialization.Serializable

@Serializable
data class GetAllAnimalsResponse(
    val resource: Resource<Animals>,
) : Response()