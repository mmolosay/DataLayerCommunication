package io.github.mmolosay.datalayercommunication.communication.impl.fakes

import io.github.mmolosay.datalayercommunication.communication.convertion.Encoder
import io.github.mmolosay.datalayercommunication.communication.models.Data

class FakeEncoder : Encoder<String> {
    override fun encode(value: String): Data =
        Data.from(value)
}