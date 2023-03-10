package io.github.mmolosay.datalayercommunication.communication.models

/**
 * Data being transfered between devices during communication process.
 * This is a output of encoding and input for decoding.
 */
@JvmInline
value class Data(val bytes: ByteArray) {

    companion object : DataFactory
}

/**
 * Factory, which creates instances of [Data], using provided sources.
 */
interface DataFactory {

    fun from(string: String): Data =
        Data(bytes = string.toByteArray())
}

fun Data.asString(): String =
    String(bytes)