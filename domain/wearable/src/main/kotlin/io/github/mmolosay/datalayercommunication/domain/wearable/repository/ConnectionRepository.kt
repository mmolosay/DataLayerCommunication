package io.github.mmolosay.datalayercommunication.domain.wearable.repository

// TODO: delete?
interface ConnectionRepository {
    suspend fun isConnectedToHandheldDevice(): Boolean
}