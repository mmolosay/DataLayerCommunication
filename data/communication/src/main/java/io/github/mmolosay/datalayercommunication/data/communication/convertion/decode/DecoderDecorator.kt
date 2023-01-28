package io.github.mmolosay.datalayercommunication.data.communication.convertion.decode

import io.github.mmolosay.datalayercommunication.domain.communication.convertion.Decoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.Encoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.Data

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