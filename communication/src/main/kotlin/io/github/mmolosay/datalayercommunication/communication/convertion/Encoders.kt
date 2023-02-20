package io.github.mmolosay.datalayercommunication.communication.convertion

import io.github.mmolosay.datalayercommunication.communication.model.Data
import io.github.mmolosay.datalayercommunication.communication.model.request.Request
import io.github.mmolosay.datalayercommunication.communication.model.response.Response

/**
 * Encodes JVM object into [Data].
 *
 * @param T most generic type of input.
 */
interface Encoder<T> {
    fun encode(value: T): Data
}

typealias RequestEncoder = Encoder<Request>

typealias ResponseEncoder = Encoder<Response>