package io.github.mmolosay.datalayercommunication.domain.wearable.repository

interface ConnectionRepository {
    suspend fun isConnectedToHandheldDevice(): Boolean
}