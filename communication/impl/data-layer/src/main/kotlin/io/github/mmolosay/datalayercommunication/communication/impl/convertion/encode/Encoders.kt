package io.github.mmolosay.datalayercommunication.communication.impl.convertion.encode

import io.github.mmolosay.datalayercommunication.communication.convertion.RequestEncoder
import io.github.mmolosay.datalayercommunication.communication.convertion.ResponseEncoder
import io.github.mmolosay.datalayercommunication.communication.models.Data
import io.github.mmolosay.datalayercommunication.communication.rpc.request.Request
import io.github.mmolosay.datalayercommunication.communication.rpc.response.Response
import kotlinx.serialization.StringFormat
import kotlinx.serialization.encodeToString

/**
 * Implementation of [RequestEncoder], powered by [kotlinx.serialization].
 */
class SerializationRequestEncoder(
    private val format: StringFormat,
) : RequestEncoder {

    override fun encode(value: Request): Data {
        val json = format.encodeToString(value)
        return Data.from(json)
    }
}

/**
 * Implementation of [ResponseEncoder], powered by [kotlinx.serialization].
 */
class SerializationResponseEncoder(
    private val format: StringFormat,
) : ResponseEncoder {

    override fun encode(value: Response): Data {
        val json = format.encodeToString(value)
        return Data.from(json)
    }
}