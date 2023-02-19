package io.github.mmolosay.datalayercommunication.communication.impl.fakes

import io.github.mmolosay.datalayercommunication.domain.communication.convertion.Encoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.Data

class FakeEncoder : Encoder<String> {
    override fun encode(value: String): Data =
        Data.from(value)
}