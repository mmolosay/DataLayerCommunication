package io.github.mmolosay.datalayercommunication.utils.resource

import io.github.mmolosay.datalayercommunication.utils.resource.Resource.Failure
import io.github.mmolosay.datalayercommunication.utils.resource.Resource.Success
import kotlinx.serialization.Serializable

/**
 * `Resource` is a [Serializable] utility component, that can be either a successful result,
 * a.k.a [Success], or [Failure], which should be hoisted to presentation.
 */
@Serializable
sealed interface Resource<out T> {

    /**
     * Successfully obtained encapsulated [value].
     */
    @Serializable
    data class Success<T>(val value: T) : Resource<T>

    /**
     * Failure, that should be delivered to user.
     */
    interface Failure : Resource<Nothing>

    companion object
}