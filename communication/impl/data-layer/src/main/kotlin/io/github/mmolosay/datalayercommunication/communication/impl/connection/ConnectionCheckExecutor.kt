package io.github.mmolosay.datalayercommunication.communication.impl.connection

fun interface ConnectionCheckExecutor {
    suspend fun checkConnectionState(): Boolean
}