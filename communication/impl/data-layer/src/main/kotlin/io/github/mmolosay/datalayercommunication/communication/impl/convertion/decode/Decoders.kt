package io.github.mmolosay.datalayercommunication.communication.impl.convertion.decode

import io.github.mmolosay.datalayercommunication.communication.convertion.RequestDecoder
import io.github.mmolosay.datalayercommunication.communication.convertion.ResponseDecoder
import io.github.mmolosay.datalayercommunication.communication.model.Data
import io.github.mmolosay.datalayercommunication.communication.model.asString
import io.github.mmolosay.datalayercommunication.communication.models.request.Request
import io.github.mmolosay.datalayercommunication.communication.models.response.Response
import kotlinx.serialization.StringFormat
import kotlinx.serialization.decodeFromString

/**
 * Implementation of [RequestDecoder], powered by [kotlinx.serialization].
 */
class SerializationRequestDecoder(
    private val format: StringFormat,
) : RequestDecoder {

    override fun decode(data: Data): Request {
        val json = data.asString()
        return format.decodeFromString(json)
    }
}

/**
 * Implementation of [ResponseDecoder], powered by [kotlinx.serialization].
 */
class SerializationResponseDecoder(
    private val format: StringFormat,
) : ResponseDecoder {

    override fun decode(data: Data): Response {
        val json = data.asString()
        return format.decodeFromString(json)
    }
}