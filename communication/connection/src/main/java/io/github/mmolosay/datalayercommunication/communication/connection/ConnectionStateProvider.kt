package io.github.mmolosay.datalayercommunication.communication.connection

import kotlinx.coroutines.flow.Flow

/**
 * Provides state of connection between current device and other one, specified in implementation.
 */
interface ConnectionStateProvider {

    /**
     * [Flow] of connection state, with `true` emitted when device connects
     * and `false` when it disconnects.
     */
    val connectionStateFlow: Flow<Boolean>
}
