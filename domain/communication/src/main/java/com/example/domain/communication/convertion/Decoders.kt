package com.example.domain.communication.convertion

import com.example.domain.communication.model.Data
import com.example.domain.communication.model.request.Request
import com.example.domain.communication.model.response.Response

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
