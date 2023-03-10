package io.github.mmolosay.datalayercommunication.communication.impl.convertion.decode

import io.github.mmolosay.datalayercommunication.communication.convertion.Decoder
import io.github.mmolosay.datalayercommunication.communication.models.Data

/**
 * [Decoder] wrapper for any other [decoder].
 * Base decorator just delegates execution to wrappped object.
 */
abstract class DecoderDecorator<T>(
    protected val decoder: Decoder<T>
) : Decoder<T> {

    override fun decode(data: Data): T =
        decoder.decode(data)
}