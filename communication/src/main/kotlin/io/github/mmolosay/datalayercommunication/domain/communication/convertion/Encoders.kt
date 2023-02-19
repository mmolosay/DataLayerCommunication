package io.github.mmolosay.datalayercommunication.domain.communication.convertion

import io.github.mmolosay.datalayercommunication.domain.communication.model.Data
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.Request
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.Response

/**
 * Encodes JVM object into byte array.
 *
 * @param T most generic type of result JVM objects.
 */
interface Encoder<T> {
    fun encode(value: T): Data
}

typealias RequestEncoder = Encoder<Request>

typealias ResponseEncoder = Encoder<Response>