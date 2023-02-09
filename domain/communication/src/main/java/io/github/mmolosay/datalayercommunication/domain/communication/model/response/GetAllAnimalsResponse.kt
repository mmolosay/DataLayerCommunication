package io.github.mmolosay.datalayercommunication.domain.communication.model.response

import io.github.mmolosay.datalayercommunication.domain.model.Animal
import io.github.mmolosay.datalayercommunication.domain.resource.Resource
import io.github.mmolosay.datalayercommunication.domain.resource.success
import kotlinx.serialization.Serializable

@Serializable
class GetAllAnimalsResponse(
    val data: List<Animal>?, // TODO: move data to Response with generic type
    val failure: Resource.Failure?, // TODO: AnimalsResource : Resource ?
) : Response()