package io.github.mmolosay.datalayercommunication.data.communication.convertion.encode

import io.github.mmolosay.datalayercommunication.domain.communication.convertion.Encoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.Data

/**
 * [Encoder] wrapper for any other [encoder].
 * Base decorator just delegates execution to wrappped object.
 */
abstract class EncoderDecorator<T>(
    protected val encoder: Encoder<T>
) : Encoder<T> {

    override fun encode(value: T): Data =
        encoder.encode(value)
}