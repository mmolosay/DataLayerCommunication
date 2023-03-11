package io.github.mmolosay.datalayercommunication.utils.resource

import io.github.mmolosay.datalayercommunication.utils.resource.Resource.Failure
import io.github.mmolosay.datalayercommunication.utils.resource.Resource.Success
import kotlinx.serialization.Serializable

/**
 * `Resource` is a [Serializable] [monad](https://en.wikipedia.org/wiki/Monad_(functional_programming)),
 * that encapsulates a result of some computation / obtaining.
 * Actual instance can be either a [Success] or a [Failure].
 *
 * There's a little to no conceptual difference between a `Resource` and a [Result], beside that
 * `Resource` is [Serializable].
 * This is required in terms of this project, since it is being sent between devices (hence between different address spaces).
 *
 * @param T type of encapsulated value.
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