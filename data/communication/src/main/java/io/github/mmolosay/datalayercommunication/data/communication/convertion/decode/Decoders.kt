package io.github.mmolosay.datalayercommunication.data.communication.convertion.decode

import io.github.mmolosay.datalayercommunication.domain.communication.convertion.RequestDecoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.ResponseDecoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.Data
import io.github.mmolosay.datalayercommunication.domain.communication.model.asString
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.Request
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.Response
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Implementation of [RequestDecoder], powered by [kotlinx.serialization].
 */
class SerializationRequestDecoder : RequestDecoder {

    override fun decode(data: Data): Request {
        val json = data.asString()
        return Json.decodeFromString(json)
    }
}

/**
 * Implementation of [ResponseDecoder], powered by [kotlinx.serialization].
 */
class SerializationResponseDecoder : ResponseDecoder {

    override fun decode(data: Data): Response {
        val json = data.asString()
        return Json.decodeFromString(json)
    }
}