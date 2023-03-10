package io.github.mmolosay.datalayercommunication.communication.convertion

import io.github.mmolosay.datalayercommunication.communication.models.Data

/**
 * Encodes JVM object into [Data].
 *
 * @param T most generic type of input.
 */
interface Encoder<T> {
    fun encode(value: T): Data
}
