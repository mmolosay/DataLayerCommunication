package io.github.mmolosay.datalayercommunication.communication.convertion

import io.github.mmolosay.datalayercommunication.communication.models.Data

/**
 * Decodes [Data] into JVM object.
 *
 * @param T most generic type of output.
 */
interface Decoder<T> {
    fun decode(data: Data): T
}
