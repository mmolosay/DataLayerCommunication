package io.github.mmolosay.datalayercommunication.communication.impl.fakes

import io.github.mmolosay.datalayercommunication.communication.convertion.Decoder
import io.github.mmolosay.datalayercommunication.communication.model.Data
import io.github.mmolosay.datalayercommunication.communication.model.asString

class FakeDecoder : Decoder<String> {
    override fun decode(data: Data): String =
        data.asString()
}