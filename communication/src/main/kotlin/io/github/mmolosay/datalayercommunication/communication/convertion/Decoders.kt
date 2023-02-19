package io.github.mmolosay.datalayercommunication.communication.convertion

import io.github.mmolosay.datalayercommunication.communication.model.Data
import io.github.mmolosay.datalayercommunication.communication.model.request.Request
import io.github.mmolosay.datalayercommunication.communication.model.response.Response

/**
 * Decodes byte array into JVM object.
 *
 * @param T most generic type of result JVM objects.
 */
interface Decoder<T> {
    fun decode(data: Data): T
}

typealias RequestDecoder = Decoder<Request>

typealias ResponseDecoder = Decoder<Response>
