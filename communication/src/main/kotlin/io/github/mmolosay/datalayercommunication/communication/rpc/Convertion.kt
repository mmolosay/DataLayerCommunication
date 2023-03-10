package io.github.mmolosay.datalayercommunication.communication.rpc

import io.github.mmolosay.datalayercommunication.communication.convertion.Decoder
import io.github.mmolosay.datalayercommunication.communication.convertion.Encoder
import io.github.mmolosay.datalayercommunication.communication.rpc.request.Request
import io.github.mmolosay.datalayercommunication.communication.rpc.response.Response

typealias RequestEncoder = Encoder<Request>

typealias ResponseEncoder = Encoder<Response>

typealias RequestDecoder = Decoder<Request>

typealias ResponseDecoder = Decoder<Response>
