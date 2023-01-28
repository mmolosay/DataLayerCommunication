package com.example.domain.communication.convertion

import com.example.domain.communication.model.Data
import com.example.domain.communication.model.request.Request
import com.example.domain.communication.model.response.Response

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