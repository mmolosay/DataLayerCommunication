package io.github.mmolosay.datalayercommunication.domain.data

import kotlinx.coroutines.flow.Flow

/**
 * Provides [Flow] of connection state between current device and other one, specified by implementation.
 */
interface ConnectionFlowProvider {

    /**
     * [Flow] of connection state, with `true` emitted when device connects
     * and `false` when it disconnects.
     */
    val connectionFlow: Flow<Boolean>
}
