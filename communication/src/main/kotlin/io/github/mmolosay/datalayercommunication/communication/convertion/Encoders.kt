package io.github.mmolosay.datalayercommunication.communication.convertion

import io.github.mmolosay.datalayercommunication.communication.models.Data
import io.github.mmolosay.datalayercommunication.communication.models.rpc.request.Request
import io.github.mmolosay.datalayercommunication.communication.models.rpc.response.Response

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