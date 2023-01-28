package io.github.mmolosay.datalayercommunication.data.communication.convertion.encode

import io.github.mmolosay.datalayercommunication.domain.communication.convertion.RequestEncoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.ResponseEncoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.Data
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.Request
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.Response
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Implementation of [RequestEncoder], powered by [kotlinx.serialization].
 */
class SerializationRequestEncoder : RequestEncoder {

    override fun encode(value: Request): Data {
        val json = Json.encodeToString(value)
        return Data.from(json)
    }
}

/**
 * Implementation of [ResponseEncoder], powered by [kotlinx.serialization].
 */
class SerializationResponseEncoder : ResponseEncoder {

    override fun encode(value: Response): Data {
        val json = Json.encodeToString(value)
        return Data.from(json)
    }
}