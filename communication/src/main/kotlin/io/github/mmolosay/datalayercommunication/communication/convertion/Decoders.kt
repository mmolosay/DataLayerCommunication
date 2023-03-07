package io.github.mmolosay.datalayercommunication.communication.convertion

import io.github.mmolosay.datalayercommunication.communication.model.Data
import io.github.mmolosay.datalayercommunication.communication.models.request.Request
import io.github.mmolosay.datalayercommunication.communication.models.response.Response

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
