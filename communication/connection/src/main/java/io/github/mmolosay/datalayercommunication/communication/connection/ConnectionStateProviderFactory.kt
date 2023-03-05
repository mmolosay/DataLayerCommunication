package io.github.mmolosay.datalayercommunication.communication.connection

interface ConnectionStateProviderFactory {
    fun createForHandheld(): ConnectionStateProvider
    fun createForWearable(): ConnectionStateProvider
}