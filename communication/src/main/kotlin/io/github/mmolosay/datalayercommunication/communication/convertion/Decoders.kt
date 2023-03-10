package io.github.mmolosay.datalayercommunication.communication.convertion

import io.github.mmolosay.datalayercommunication.communication.models.Data
import io.github.mmolosay.datalayercommunication.communication.rpc.request.Request
import io.github.mmolosay.datalayercommunication.communication.rpc.response.Response

/**
 * Decodes [Data] into JVM object.
 *
 * @param T most generic type of output.
 */
interface Decoder<T> {
    fun decode(data: Data): T
}

typealias RequestDecoder = Decoder<Request>

typealias ResponseDecoder = Decoder<Response>
