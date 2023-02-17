package io.github.mmolosay.datalayercommunication.data.communication.convertion

import io.github.mmolosay.datalayercommunication.domain.communication.convertion.Decoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.Encoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.Request
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.Response

/**
 * Pair of [encoder] and [decoder], that are assembled with same features
 * and destined to be used together.
 */
data class Converters<T>(
    val encoder: Encoder<T>,
    val decoder: Decoder<T>,
)

typealias RequestConverters = Converters<Request>

typealias ResponseConverters = Converters<Response>