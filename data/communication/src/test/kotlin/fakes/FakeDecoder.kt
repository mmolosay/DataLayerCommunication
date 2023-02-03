package fakes

import io.github.mmolosay.datalayercommunication.domain.communication.convertion.Decoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.Data
import io.github.mmolosay.datalayercommunication.domain.communication.model.asString

class FakeDecoder : Decoder<String> {
    override fun decode(data: Data): String =
        data.asString()
}