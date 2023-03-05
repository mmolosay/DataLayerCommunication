package io.github.mmolosay.datalayercommunication.communication.connection

import kotlinx.coroutines.flow.Flow

/**
 * Provides state of connection between current device and other one, specified by implementation.
 */
// It is placed in individual module, because it can be used by both handheld and wearable apps.
interface ConnectionStateProvider {

    /**
     * [Flow] of connection state, with `true` emitted when device connects
     * and `false` when it disconnects.
     */
    val connectionStateFlow: Flow<Boolean>
}
