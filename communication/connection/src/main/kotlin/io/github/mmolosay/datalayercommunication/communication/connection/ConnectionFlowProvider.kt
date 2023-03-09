package io.github.mmolosay.datalayercommunication.communication.connection

import kotlinx.coroutines.flow.Flow

/**
 * Provides [Flow] of connection state between current device and other one, specified by implementation.
 */
/*
 * It is placed in individual module, because it can be used by both handheld and wearable apps.
 * Conceptually, it is not different from any Repository in 'data' layer, since it's an abstraction
 * as well, thus stable.
 */
// TODO: move in ':domain:common'?
//       components in ':communication' are designed to be used in data layer.
//       but this component is used by domain / presentaiton
interface ConnectionFlowProvider {

    /**
     * [Flow] of connection state, with `true` emitted when device connects
     * and `false` when it disconnects.
     */
    val connectionFlow: Flow<Boolean>
}
