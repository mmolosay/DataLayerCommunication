package io.github.mmolosay.datalayercommunication.communication.impl.fakes

import io.github.mmolosay.datalayercommunication.communication.convertion.Decoder
import io.github.mmolosay.datalayercommunication.communication.models.Data
import io.github.mmolosay.datalayercommunication.communication.models.asString

class FakeDecoder : Decoder<String> {
    override fun decode(data: Data): String =
        data.asString()
}