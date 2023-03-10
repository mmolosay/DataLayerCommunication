package io.github.mmolosay.datalayercommunication.communication.impl.convertion.encode

import io.github.mmolosay.datalayercommunication.communication.convertion.Encoder
import io.github.mmolosay.datalayercommunication.communication.models.Data

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