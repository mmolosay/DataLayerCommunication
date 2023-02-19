package io.github.mmolosay.datalayercommunication.domain.communication.convertion

import io.github.mmolosay.datalayercommunication.domain.communication.model.Data
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.Request
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.Response

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
